# Projeto da Disciplina IN1007 - Paradigmas de Linguagem de Programação

Este repositório contém o projeto desenvolvido na disciplina **IN1007 - Paradigmas de Linguagem de Programação**, que faz parte do **Programa de Pós-Graduação em Ciência da Computação (PPGCC)** no **Centro de Informática da Universidade Federal de Pernambuco (CIn-UFPE)**.

## Integrantes do Projeto

- **Larissa Azevedo Marques Cavalcanti de Albuquerque** - lamca@cin.ufpe.br
- **Pedro Henrique Lopes dos Santos** - phls2@cin.ufpe.br

## Sobre a Disciplina
Informações sobre a disciplina podem ser encontradas no site oficial: [IN1007 - UFPE](https://www.cin.ufpe.br/~in1007/).

# Descrição do Projeto

## Decorators para funções e métodos
Este projeto implementa suporte a decorators para funções e métodos em uma linguagem customizada criada com Java e JavaCC, inspirado no [PEP 318 — Decorators for Functions and Methods](https://peps.python.org/pep-0318/).
Decorators permitem modificar ou estender o comportamento de funções de forma modular, reutilizável e elegante. Com eles, é possível adicionar funcionalidades como logging, validação, memoização, entre outros — sem alterar diretamente o corpo da função.

## Escopo do Projeto
Estender a Linguagem Funcional 1 apresentada na disciplina para que ela ofereça suporte à aplicação de decorators sobre definições de funções. O objetivo é permitir que funções sejam modificadas ou estendidas de forma modular, por meio da sintaxe @nomeDoDecorator logo antes da declaração da função.

```python
def log(func):
    def wrapper(a, b):
        print("Chamando função com argumentos:", a, b)
        resultado = func(a, b)
        print("Resultado:", resultado)
        return resultado
    return wrapper

@log
def soma(x, y):
    return x + y

soma(3, 4)
```

- log é um decorator que envolve a função original com um comportamento extra (mensagens).
- @log é a forma declarativa de aplicar o decorator.
- O resultado é uma função com o mesmo nome e comportamento da original, mas com comportamento adicional sem alterar o corpo da função.

## Gramática

<pre>
Programa ::= Expressao

Expressao ::= Valor
            | ExpUnaria
            | ExpBinaria
            | ExpDeclaracao
            | Id
            | Aplicacao
            | IfThenElse

Valor ::= ValorConcreto

ValorConcreto ::= ValorInteiro
                | ValorBooleano
                | ValorString

ExpUnaria ::= "-" Expressao
            | "not" Expressao
            | "length" Expressao

ExpBinaria ::= Expressao "+" Expressao
             | Expressao "-" Expressao
             | Expressao "and" Expressao
             | Expressao "or" Expressao
             | Expressao "==" Expressao
             | Expressao "++" Expressao

ExpDeclaracao ::= "let" DeclaracaoFuncional "in" Expressao

DeclaracaoFuncional ::= DecVariavel
                      | DecFuncaoComDecorator
                      | DecComposta

DecVariavel ::= "var" Id "=" Expressao

DecFuncaoComDecorator ::= Decorators? DecFuncao

Decorators ::= Decorator+
    
Decorator ::= "@" Id

DecFuncao ::= "fun" ListId "=" Expressao

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListId ::= Id | Id ListId

Aplicacao ::= Id "(" ListExp ")"

ListExp ::= Expressao | Expressao "," ListExp

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao
</pre>

## O que tem de novo?

<pre>
DeclaracaoFuncional ::= DecVariavel
                      | DecFuncaoComDecorator
                      | DecComposta

DecFuncaoComDecorator ::= Decorators? DecFuncao

Decorators ::= Decorator+
    
Decorator ::= "@" Id
</pre>


## Como Executar
```
mvn package
```
- Este comando builda todo o projeto, todas as linguagens com o Applet.
```
java -jar Applet/target/Applet-0.0.1-jar-with-dependencies.jar
```
- Este comando executa o app java.
