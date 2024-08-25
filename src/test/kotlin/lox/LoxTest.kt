package lox

import kotlin.test.BeforeTest

open class LoxTest {
    @BeforeTest
    fun setUp() {
        Lox.reset()
    }
}
