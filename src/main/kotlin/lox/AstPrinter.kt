package lox

class AstPrinter : Visitor<String> {
    fun print(expr: Expr) = expr.accept(this)

    override fun visitBinaryExpr(expr: Binary): String {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right)
    }

    override fun visitGroupingExpr(expr: Grouping): String {
        return parenthesize("group", expr.expression)
    }

    override fun visitLiteralExpr(expr: Literal): String {
        return expr.value?.toString() ?: "nil"
    }

    override fun visitUnaryExpr(expr: Unary): String {
        return parenthesize(expr.operator.lexeme, expr.right)
    }

    override fun visitTernaryExpr(expr: Ternary): String {
        return parenthesize("?:", expr.comparison, expr.left, expr.right)
    }

    private fun parenthesize(name: String, vararg exprs: Expr) = buildString {
        append("($name")
        for (expr in exprs) {
            append(" ${expr.accept(this@AstPrinter)}")
        }
        append(")")
    }
}
