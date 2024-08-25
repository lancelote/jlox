package lox

import lox.TokenType.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AstPrinterTest : LoxTest() {
    @Test
    fun `print without parsing`() {
        val expression = Binary(
            Unary(
                Token(MINUS, "-", null, 1),
                Literal(123),
            ),
            Token(STAR, "*", null, 1),
            Grouping(Literal(45.67))
        )
        assertEquals("(* (- 123) (group 45.67))", AstPrinter().print(expression))
    }
}
