package lox

import lox.TokenType.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ScannerTest {
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
}
