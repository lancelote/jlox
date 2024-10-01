# jlox

My implementation of `jlox` programming language from "[Crafting Interpreters][book]" book by Robert Nystrom 

## TOC

* [x] Chapter 4: Scanning
* [x] Chapter 5: Representing Code
* [x] Chapter 6: Parsing Expressions
* [ ] Chapter 7: Evaluating Expressions
* [ ] Chapter 8: Statements and State
* [ ] Chapter 9: Control Flow
* [ ] Chapter 10: Functions
* [ ] Chapter 11: Resolving and Binding
* [ ] Chapter 12: Classes
* [ ] Chapter 13: Inheritance

## BNF

```
expression -> equality ;
equality   -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term       -> factor ( ( "-" | "+" ) factor )* ;
factor     -> unary ( ( "/" | "*" ) unary )* ;
unary      -> ( "!" | "-") unary | ternary ;
ternary    -> comma ":" comma "?" comma ; 
comma      -> primary ( "," primary )* ;
primary    -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;
```

# Precedence

| name       | operators         | associates |
|------------|-------------------|------------|
| equality   | `==` `!=`         | left       |
| comparison | `>` `>=` `<` `<=` | left       |
| term       | `-` `+`           | left       |
| factor     | `/` `*`           | left       |
| unary      | `!` `-`           | right      |
| ternary    | `?:`              | right      |
| comma      | `,`               | left       |

[book]: https://craftinginterpreters.com/
