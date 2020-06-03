# language_example_summer20

## Language ##

```
i is an integer
e is an expression
e ::= i | e1 '+' e2
```

### Tokens ###

- Integer tokens (which hold an integer)
- Plus token (holds nothing)

```
1 + 2: [INTEGER_TOKEN(1), PLUS_TOKEN, INTEGER_TOKEN(2)]
```
