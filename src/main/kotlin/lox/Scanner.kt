package lox

import lox.TokenType.*

class Scanner(private val source: String) {
    private val tokens = ArrayList<Token>()
    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<Token> {
        while (!isAtEnd) {
            start = current
            scanToken()
        }

        tokens.add(Token(EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        when (val char = advance()) {
            '(' -> addToken(LEFT_PAREN)
            ')' -> addToken(RIGHT_PAREN)
            '{' -> addToken(LEFT_BRACE)
            '}' -> addToken(RIGHT_BRACE)
            ',' -> addToken(COMMA)
            '.' -> addToken(DOT)
            '-' -> addToken(MINUS)
            '+' -> addToken(PLUS)
            ';' -> addToken(SEMICOLON)
            '*' -> addToken(STAR)
            '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
            '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)
            '/' -> {
                if (match('/')) {
                    // a comment goes until the end of the line
                    while (peek() != '\n' && !isAtEnd) advance()
                } else {
                    addToken(SLASH)
                }
            }
            ' ', '\r', '\t' -> {}
            '\n' -> line++
            '"' -> string()
            else -> {
                if (char.isDigit()) {
                    number()
                } else {
                    Lox.reportError(line, "unexpected character: $char")
                }
            }
        }
    }

    private fun number() {
        while (peek()?.isDigit() == true) advance()

        if (peek() == '.' && peekNext()?.isDigit() == true) {
            advance() // consume `.`

            while (peek()?.isDigit() == true) advance()
        }

        addToken(NUMBER, source.substring(start, current).toDouble())
    }

    private fun peekNext() = source.getOrNull(current + 1)

    private fun string() {
        while (peek() != '"' && !isAtEnd) {
            if (peek() == '\n') line++
            advance()
        }

        if (isAtEnd) {
            Lox.reportError(line, "unterminated string")
            return
        }

        advance() // consume the closing `"`

        val value = source.substring(start + 1, current - 1) // trim quotes
        addToken(STRING, value)
    }

    private fun peek() = source.getOrNull(current)

    private fun match(expected: Char): Boolean {
        if (isAtEnd) return false
        if (source[current] != expected) return false

        current++
        return true
    }

    private fun advance() = source[current++]

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text: String = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

    private val isAtEnd get() = current >= source.length
}
