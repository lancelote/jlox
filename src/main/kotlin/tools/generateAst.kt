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
    val path = joinPath("$baseName.kt")
    val file = File(path)
    val writer = file.bufferedWriter()

    val lines = mutableListOf(
        "package lox",
        "",
        "abstract class $baseName",
        "",
    )

    fun defineType(className: String, fields: Map<String, String>) {
        lines.add("data class $className(")

        for ((key, value) in fields) {
            lines.add("    val $key: $value,")
        }

        lines.add(") : Expr()")
        lines.add("")
    }

    fieldDeclarations.forEach { fieldDescription ->
        val (left, right) = fieldDescription.split(":")
        val className = left.trim()
        val fields = right.trim().split(", ").associate {
            val (name, type) = it.split(" ")
            name to type
        }

        defineType(className, fields)
    }

    writer.use { out -> lines.forEach { out.writeLn(it) } }
}
