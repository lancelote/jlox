package lox

abstract class Expr {
    abstract fun <T> accept(visitor: Visitor<T>): T
}

data class Binary(
    val left: Expr,
    val operator: Token,
    val right: Expr,
) : Expr() {
    override fun <T> accept(visitor: Visitor<T>) = visitor.visitBinaryExpr(this)
}

data class Grouping(
    val expression: Expr,
) : Expr() {
    override fun <T> accept(visitor: Visitor<T>) = visitor.visitGroupingExpr(this)
}

data class Literal(
    val value: Any?,
) : Expr() {
    override fun <T> accept(visitor: Visitor<T>) = visitor.visitLiteralExpr(this)
}

data class Unary(
    val operator: Token,
    val right: Expr,
) : Expr() {
    override fun <T> accept(visitor: Visitor<T>) = visitor.visitUnaryExpr(this)
}

interface Visitor<T> {
    fun visitBinaryExpr(expr: Binary): T
    fun visitGroupingExpr(expr: Grouping): T
    fun visitLiteralExpr(expr: Literal): T
    fun visitUnaryExpr(expr: Unary): T
}
