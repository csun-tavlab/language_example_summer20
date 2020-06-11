# language_example_summer20

# Language #

### Abstract Grammar ###

```
x is a variable
i is an integer
e is an expression
op is an operator

op ::= '+' | '-' | '*' | '/' | '&&' | '||'
e ::= i | x | true | false | e1 op e2
```

### Concrete Grammar ###

Precedence levels, from lowest to highest (based on what Java does):

- `||`
- `&&`
- `+`, `-`
- `*`, `/`

```
i is an integer
e is an expression
and is an and expression
or is an or expression
a is an additive expression
m is a multiplicative expression
p is a primary expression

e ::= or
or ::= and ('||' and)*
and ::= a ('&&' a)*
a ::= m (('+' | '-') m)*
m ::= p (('/' | '*') p)*
p ::= i | x | true | false | '(' e ')'
```

### Tokens ###

- Integer tokens (which hold an integer)
- Plus token (holds nothing)

```
1 + 2: [INTEGER_TOKEN(1), PLUS_TOKEN, INTEGER_TOKEN(2)]
```
