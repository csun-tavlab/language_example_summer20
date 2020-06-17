# language_example_summer20

# Language #

### Abstract Grammar ###

```
x is a variable
i is an integer
e is an expression
op is an operator
t is a type
s is a statement
prog is a program

t ::= 'int' | 'bool'
s ::= t x '=' e ';'
op ::= '+' | '-' | '*' | '/' | '&&' | '||'
e ::= i | x | true | false | e1 op e2
prog ::= s*
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
t ::= 'int' | 'bool'
s ::= t x '=' e ';'
prog ::= s*
```

## Running the Compiler ##

```
mvn exec:java -Dexec.mainClass="example.ExampleCompiler" -Dexec.args="input_program.txt output_program.js"
```

...where

- `input_program.txt`: the input program in this example language
- `output_program.js`: name of an output file where equivalent JavaScript will be written
