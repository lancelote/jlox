package lox

abstract class Expr {
    abstract fun <T> accept(visitor: Visitor<T>)
}

data class Binary(
    val left: Expr,
    val operator: Token,
    val right: Expr,
) : Expr()

data class Grouping(
    val expression: Expr,
) : Expr()

data class Literal(
    val value: Any?,
) : Expr()

data class Unary(
    val operator: Token,
    val right: Expr,
) : Expr()

interface Visitor<T> {
    fun visitBinaryExpr(expr: Binary): T
    fun visitGroupingExpr(expr: Grouping): T
    fun visitLiteralExpr(expr: Literal): T
    fun visitUnaryExpr(expr: Unary): T
}
