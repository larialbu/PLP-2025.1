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

### Exemplo:
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
<a href="Funcional1/src/lf1/plp/functional1/Programa.java">Programa</a> ::= <a href="Funcional1/src/lf1/plp/expressions2/expression/Expressao.java">Expressao</a>

Expressao ::= <a href="Funcional1/src/lf1/plp/expressions2/expression/Valor.java">Valor</a>
            | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpUnaria.java">ExpUnaria</a> 
            | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpBinaria.java">ExpBinaria</a>
            | <a href="Funcional1/src/lf1/plp/functional1/expression/ExpDeclaracao.java">ExpDeclaracao</a>
            | <a href="Funcional1/src/lf1/plp/expressions2/expression/Id.java">Id</a>
            | <a href="Funcional1/src/lf1/plp/functional1/expression/Aplicacao.java">Aplicacao</a>
            | <a href="Funcional1/src/lf1/plp/functional1/expression/IfThenElse.java">IfThenElse</a>

Valor ::= <a href="Funcional1/src/lf1/plp/expressions2/expression/ValorConcreto.java">ValorConcreto</a> 

ValorConcreto ::= <a href="Funcional1/src/lf1/plp/expressions2/expression/ValorInteiro.java">ValorInteiro</a> 
                | <a href="Funcional1/src/lf1/plp/expressions2/expression/ValorBooleano.java">ValorBooleano</a>
                | <a href="Funcional1/src/lf1/plp/expressions2/expression/ValorString.java">ValorString</a>

ExpUnaria ::= <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpMenos.java">"-" Expressao</a>
            | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpNot.java">"not" Expressao</a>
            | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpLength.java">"length" Expressao</a>

ExpBinaria ::= <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpSoma.java">Expressao "+" Expressao</a>
             | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpSub.java">Expressao "-" Expressao</a>
             | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpAnd.java">Expressao "and" Expressao</a>
             | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpOr.java">Expressao "or" Expressao</a>
             | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpEquals.java">Expressao "==" Expressao</a>
             | <a href="Funcional1/src/lf1/plp/expressions2/expression/ExpConcat.java">Expressao "++" Expressao</a>

ExpDeclaracao ::= "let" <a href="Funcional1/src/lf1/plp/functional1/declaration/DeclaracaoFuncional.java">DeclaracaoFuncional</a> "in" Expressao

DeclaracaoFuncional ::= <a href="Funcional1/src/lf1/plp/functional1/declaration/DeclaracaoVariavel.java">DecVariavel</a>
                      | DecFuncaoComDecorator
                      | <a href="Funcional1/src/lf1/plp/functional1/declaration/DeclaracaoComposta.java">DecComposta</a>

DecVariavel ::= "var" Id "=" Expressao

DecFuncaoComDecorator ::= Decorator* <a href="Funcional1/src/lf1/plp/functional1/declaration/DeclaracaoFuncao.java">DecFuncao</a>
    
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

DecFuncaoComDecorator ::= Decorator* DecFuncao
    
Decorator ::= "@" Id
</pre>


## Como Executar

### 1. Para compilar todas as linguagens e buildar o applet (interpretador):
```bash
mvn package
java -jar Applet/target/Applet-0.0.1-jar-with-dependencies.jar
```

### 2. Para compilação individual de cada linguagem:
```bash
cd <pathDaLinguagem>
mvn clean generate-sources compile exec:java
```

### 3. Usando makefile:
```bash
make plp
```
