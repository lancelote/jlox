package lox

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParserTest : LoxTest() {
    @Test
    fun `basic expression`() {
        val scanner = Scanner("1 + 2")
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        val expr = parser.parse()!!

        assertEquals("(+ 1.0 2.0)", AstPrinter().print(expr))
    }

    @Test
    fun `parse unclosed parenthesis`() {
        val scanner = Scanner("(")
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        val expr = parser.parse()

        assertTrue(Lox.hadError)
        assertNull(expr)
    }

    @Test
    fun `parse wrong syntax`() {
        val scanner = Scanner("* 42")
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        val expr = parser.parse()

        assertTrue(Lox.hadError)
        assertNull(expr)
    }
}
