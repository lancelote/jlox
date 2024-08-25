package lox

import lox.TokenType.*
import java.io.File
import kotlin.system.exitProcess

class Lox {
    companion object {
        var hadError = false
            private set

        private fun runSource(source: String) {
            val scanner = Scanner(source)
            val tokens = scanner.scanTokens()
            val parser = Parser(tokens)
            val expr = parser.parse()

            if (hadError) return

            println(AstPrinter().print(expr!!))
        }

        fun runFile(path: String) {
            runSource(File(path).readText())

            if (hadError) exitProcess(65)
        }

        fun runPrompt() {
            while (true) {
                print("> ")
                val line = readlnOrNull() ?: break
                runSource(line)
                hadError = false
            }
        }

        fun error(line: Int, message: String) {
            report(line, "", message)
        }

        fun error(token: Token, message: String) {
            if (token.type == EOF) {
                report(token.line, "at end", message)
            } else {
                report(token.line, "at '${token.lexeme}'", message)
            }
        }

        private fun report(line: Int, where: String, message: String) {
            System.err.println("[line $line] Error $where: $message")
            hadError = true
        }
    }
}

fun main(args: Array<String>) {
    if (args.size > 1) {
        println("Usage: jlox [script]")
        exitProcess(64)
    } else if (args.size == 1) {
        Lox.runFile(args[0])
    } else {
        Lox.runPrompt()
    }
}
