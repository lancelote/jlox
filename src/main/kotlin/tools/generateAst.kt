package tools

import java.io.BufferedWriter
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("usage: generate_ast <output directory>")
        exitProcess(64)
    }
    defineAst("Expr", listOf(
        "Binary   : left Expr, operator Token, right Expr",
        "Grouping : expression Expr",
        "Literal  : value Any?",
        "Unary    : operator Token, right Expr",
    ))
}

fun joinPath(vararg segments: String) = segments.joinToString(File.separator)

fun BufferedWriter.writeLn(line: String) {
    this.write(line)
    this.newLine()
}

fun defineAst(baseName: String, fieldDeclarations: List<String>) {
    val path = joinPath("src", "main", "kotlin", "lox", "$baseName.kt")
    val file = File(path)
    val writer = file.bufferedWriter()

    fun parseFieldDeclarations() = fieldDeclarations.associate { fd ->
        val (left, right) = fd.split(":")
        val className = left.trim()
        className to right.trim().split(", ").associate {
            val (name, type) = it.split(" ")
            name to type
        }
    }

    val dataClasses = parseFieldDeclarations()

    val lines = mutableListOf<String>()

    fun defineHeader() {
        lines.add("package lox")
        lines.add("")
    }

    fun defineBase() {
        lines.add("abstract class $baseName {")
        lines.add("    abstract fun <T> accept(visitor: Visitor<T>): T")
        lines.add("}")
        lines.add("")
    }

    fun defineType(className: String, fields: Map<String, String>) {
        lines.add("data class $className(")

        for ((key, value) in fields) {
            lines.add("    val $key: $value,")
        }

        lines.add(") : Expr() {")
        lines.add("    override fun <T> accept(visitor: Visitor<T>) = visitor.visit$className$baseName(this)")
        lines.add("}")
        lines.add("")
    }

    fun defineVisitor() {
        lines.add("interface Visitor<T> {")

        dataClasses.forEach { (className, _) ->
            lines.add("    fun visit$className$baseName(${baseName.lowercase()}: $className): T")
        }

        lines.add("}")
    }

    defineHeader()
    defineBase()
    dataClasses.forEach { (className, fields) ->
        defineType(className, fields)
    }
    defineVisitor()

    writer.use { out -> lines.forEach { out.writeLn(it) } }
}
