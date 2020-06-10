# language_example_summer20

# Language #

### Abstract Grammar ###

```
i is an integer
e is an expression
op is an operator

op ::= '+' | '-' | '*' | '/' 
e ::= i | e1 op e2
```

### Concrete Grammar ###

```
i is an integer
e is an expression
a is an additive expression
m is a multiplicative expression
p is a primary expression

e ::= a
a ::= m (('+' | '-') m)*
m ::= p (('/' | '*') p)*
p ::= i | '(' e ')'
```

### Tokens ###

- Integer tokens (which hold an integer)
- Plus token (holds nothing)

```
1 + 2: [INTEGER_TOKEN(1), PLUS_TOKEN, INTEGER_TOKEN(2)]
```
