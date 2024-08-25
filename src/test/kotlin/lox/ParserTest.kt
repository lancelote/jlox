package lox

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParserTest : LoxTest() {
    private fun parseToExpr(source: String): Expr? {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        return parser.parse()
    }

    private fun assertParseFailure(source: String) {
        val expr = parseToExpr(source)

        assertTrue(Lox.hadError)
        assertNull(expr)
    }

    private fun assertParsePrint(expected: String, source: String) {
        val expr = parseToExpr(source)!!

        assertEquals(expected, AstPrinter().print(expr))
    }

    @Test
    fun `basic expression`() {
        assertParsePrint("(+ 1.0 2.0)", "1 + 2")
    }

    @Test
    fun `parse unclosed parenthesis`() {
        assertParseFailure("(")
    }

    @Test
    fun `parse wrong syntax`() {
        assertParseFailure("* 42")
    }

    @Test
    fun `parse comma operator`() {
        assertParsePrint("(, 1.0 2.0)", "1, 2")
    }
}
