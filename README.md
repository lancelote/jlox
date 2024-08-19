# jlox

My implementation of `jlox` programming language from "[Crafting Interpreters][book]" book by Robert Nystrom 

## TOC

* [x] Chapter 4: Scanning
* [x] Chapter 5: Representing Code
* [ ] Chapter 6: Parsing Expressions
* [ ] Chapter 7: Evaluating Expressions
* [ ] Chapter 8: Statements and State
* [ ] Chapter 9: Control Flow
* [ ] Chapter 10: Functions
* [ ] Chapter 11: Resolving and Binding
* [ ] Chapter 12: Classes
* [ ] Chapter 13: Inheritance

## BNF

```
expression -> literal | unary | binary | grouping ;
literal    -> NUMBER | STRING | "true" | "false" | "nil" ;
grouping   -> "(" expression ")" ;
unary      -> ( "-" | "!" ) expression ;
binary     -> expression operator expression ;
operator   -> "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" ;
```

[book]: https://craftinginterpreters.com/
