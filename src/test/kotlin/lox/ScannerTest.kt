package lox

import lox.TokenType.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ScannerTest : LoxTest() {
    @Test
    fun `basic scanner text`() {
        val scanner = Scanner("""
            // this is a comment
            (( )){} // grouping stuff
            !*+-/=<> <= == // operators
        """.trimIndent())

        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(LEFT_PAREN, "(", null, 2),
            Token(LEFT_PAREN, "(", null, 2),
            Token(RIGHT_PAREN, ")", null, 2),
            Token(RIGHT_PAREN, ")", null, 2),
            Token(LEFT_BRACE, "{", null, 2),
            Token(RIGHT_BRACE, "}", null, 2),
            Token(BANG, "!", null, 3),
            Token(STAR, "*", null, 3),
            Token(PLUS, "+", null, 3),
            Token(MINUS, "-", null, 3),
            Token(SLASH, "/", null, 3),
            Token(EQUAL, "=", null, 3),
            Token(LESS, "<", null, 3),
            Token(GREATER, ">", null, 3),
            Token(LESS_EQUAL, "<=", null, 3),
            Token(EQUAL_EQUAL, "==", null, 3),
            Token(EOF, "", null, 3),
        )
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `string literal basic`() {
        val scanner = Scanner("\"hello \" + \"world\"")
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(STRING, "\"hello \"", literal = "hello ", 1),
            Token(PLUS, "+", null, 1),
            Token(STRING, "\"world\"", literal = "world", 1),
            Token(EOF, "", null, 1),
        )
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `unterminated string`() {
        val scanner = Scanner("\"hello world")
        assertFalse(Lox.hadError)
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(EOF, "", null, 1)
        )
        assertEquals(expectedTokens, tokens)
        assertTrue(Lox.hadError)
    }

    @Test
    fun `multiline string`() {
        val scanner = Scanner("""
            "hello 
            world"
        """.trimIndent())
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(STRING, "\"hello \nworld\"", "hello \nworld", 2),
            Token(EOF, "", null, 2),
        )
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `scanning integer`() {
        val scanner = Scanner("42")
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(NUMBER, "42", 42.0, 1),
            Token(EOF, "", null, 1),
        )
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `scanning double`() {
        val scanner = Scanner("12.34")
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(NUMBER, "12.34", 12.34, 1),
            Token(EOF, "", null, 1),
        )
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `scanning keyword & identifier`() {
        val scanner = Scanner("false falsehood")
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(FALSE, "false", null, 1),
            Token(IDENTIFIER, "falsehood", null, 1),
            Token(EOF, "", null, 1),
        )
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `scanning multiline comment`() {
        val scanner = Scanner("""
            /* foo
            bar
            baz */
            42
        """.trimIndent())
        val tokens = scanner.scanTokens()
        val expectedTokens = listOf(
            Token(NUMBER, "42", 42.0, 4),
            Token(EOF, "", null, 4),
        )
        assertEquals(expectedTokens, tokens)
    }
}
