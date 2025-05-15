# Projeto da Disciplina IN1007 - Paradigmas de Linguagem de Programação

Este repositório contém o projeto desenvolvido na disciplina **IN1007 - Paradigmas de Linguagem de Programação**, que faz parte do **Programa de Pós-Graduação em Ciência da Computação (PPGCC)** no **Centro de Informática da Universidade Federal de Pernambuco (CIn-UFPE)**.

## Integrantes do Projeto

- **Larissa Azevedo Marques Cavalcanti de Albuquerque** - lamca@cin.ufpe.br
- **Pedro Henrique Lopes dos Santos** - phls2@cin.ufpe.br

## Sobre a Disciplina
Informações sobre a disciplina podem ser encontradas no site: [IN1007 - UFPE](https://www.cin.ufpe.br/~in1007/).

# Descrição do Projeto

## Decorators para funções e métodos
Este projeto implementa suporte a decorators para funções e métodos em uma linguagem customizada criada com Java e JavaCC, inspirado no [PEP 318 — Decorators for Functions and Methods](https://peps.python.org/pep-0318/) para Python.
Decorators permitem modificar ou estender o comportamento de funções de forma modular, reutilizável e elegante. Com eles, é possível adicionar funcionalidades como logging, validação, memoização, entre outros — sem alterar diretamente o corpo da função.

## Escopo do Projeto
Estender a Linguagem Imperativa 2 apresentada na disciplina para que ela ofereça suporte à aplicação de decorators sobre definições de funções. O objetivo é permitir que funções sejam modificadas ou estendidas de forma modular, por meio da sintaxe @nomeDoDecorator logo antes da declaração da função.

### Exemplo de como Decorators funcionam em Python:
Código:
```python
def log(func):
    def wrapper(a, b):
        print("Chamando função com argumentos:", a, b)
        resultado = func(a, b)
        print("Resultado:", resultado)
    return wrapper

@log
def soma(x, y):
    return x + y

soma(3, 4)
```
Output:
```
Chamando função com argumentos: 3 4
Resultado: 7
```

- log é um decorator que envolve a função original com um comportamento extra (mensagens).
- @log é a forma declarativa de aplicar o decorator.
- O resultado é uma função com o mesmo nome e comportamento da original, mas com comportamento adicional sem alterar o corpo da função.

### Exemplo de como os decorators funcionariam na linguagem da disciplina:

```
proc log(func(int, int) -> int f, int a, int b) {
    write("Chamando função com argumentos:");
    write(a);
    write(b);
    var resultado = f(a, b);
    write("Resultado:");
    write(resultado);
}

proc soma(int x, int y) {
    return x + y;
}

@log call soma(3, 4);
```

### Similaridades e Diferenças
As duas linguagens estão fazendo a mesma coisa: passando uma função como argumento, chamando-a, e adicionando conteúdo antes e depois, mas existem diferenças:
Em python, os decorators são aplicados na declaração da função como no código a seguir:
```python
@log
def soma(x, y):
return x + y
```
Isso permite aplicar decorators automaticamente a qualquer chamada futura da função soma.
Na nossa linguagem, optamos por associar os decorators à chamada das funções, permitindo aplicar o efeito do decorator apenas quando o desenvolvedor quiser, como mostra o código a seguir:
```
@log call soma(3, 4);
```
Igualmente, decorators encadeáveis podem ser definidos da seguinte forma:
```
@log @validate call soma(3, 4);
```

## Gramática EBNF

<pre>
    <a href="../plp/Imperativa2/src/li2/plp/imperative2/Programa.java">Programa</a> ::= <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Comando.java">Comando</a>
    
    Comando ::= <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Atribuicao.java">Atribuicao</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/ComandoDeclaracao.java">ComandoDeclaracao</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/While.java">While</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/IfThenElse.java">IfThenElse</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/IO.java">IO</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/SequenciaComando.java">Comando &quot;;&quot; Comando</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Skip.java">Skip</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative2/command/ChamadaProcedimento.java">ChamadaProcedimento</a>
    
    Skip ::= 
    
    Atribuicao ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a> &quot;:=&quot; <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a>
    
    Expressao ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/Valor.java">Valor</a> 
                | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpUnaria.java">ExpUnaria</a> 
                | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpBinaria.java">ExpBinaria</a> 
                | Id "(" [ ListaExpressao ] ")" 
                | Id
    
    Valor ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ValorConcreto.java">ValorConcreto</a>
   
    ValorConcreto ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ValorInteiro.java">ValorInteiro</a> | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ValorBooleano.java">ValorBooleano</a> | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ValorString.java">ValorString</a>
    
    ExpUnaria ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpMenos.java">&quot;-&quot; Expressao</a> | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpNot.java">&quot;not&quot; Expressao</a> | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpLength.java">&quot;length&quot; Expressao</a>
    
    ExpBinaria ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpSoma.java">Expressao &quot;+&quot; Expressao</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpSub.java">Expressao &quot;-&quot; Expressao</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpAnd.java">Expressao &quot;and&quot; Expressao</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpOr.java">Expressao &quot;or&quot; Expressao</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpEquals.java">Expressao &quot;==&quot; Expressao</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpConcat.java">Expressao &quot;++&quot; Expressao</a>
    
    ComandoDeclaracao :: = &quot;{&quot; <a href="../plp/Imperativa2/src/li2/plp/imperative1/declaration/Declaracao.java">Declaracao</a> &quot;;&quot; Comando &quot;}&quot;
    
    Declaracao ::= <a href="../plp/Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoVariavel.java">DeclaracaoVariavel</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java">DeclaracaoProcedimento</a>
                 | <a href="../plp/Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoComposta.java">DeclaracaoComposta</a>
    
    DeclaracaoVariavel ::= &quot;var&quot; Id &quot;=&quot; Expressao&nbsp;
  
    DeclaracaoComposta ::= Declaracao &quot;,&quot; Declaracao
  
    DeclaracaoProcedimento ::= <a href="../plp/Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java">&quot;proc&quot; Id &quot;(&quot; [ ListaDeclaracaoParametro ] &quot;)&quot; &quot;{&quot; Comando &quot;}&quot;</a>
  
    ListaDeclaracaoParametro ::= <a href="../plp/Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoParametro.java">Tipo Id</a> | <a href="../plp/Imperativa2/src/li2/plp/imperative2/declaration/ListaDeclaracaoParametro.java">Tipo Id &quot;,&quot; ListaDeclaracaoParametro</a>

    Tipo ::= TipoPrimitivo | TipoFuncao
    
    TipoPrimitivo ::= &quot;string&quot; | &quot;int&quot; | &quot;boolean&quot;

    TipoFuncao ::= "func" "(" [ ListaTipos ] ")" "->" Tipo

    ListaTipos ::= Tipo | Tipo "," ListaTipos
    
    While ::= &quot;while&quot; Expressao &quot;do&quot; Comando
  
    IfThenElse ::= &quot;if&quot; Expressao &quot;then&quot; Comando &quot;else&quot; Comando
  
    IO ::= <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Write.java">&quot;write&quot; &quot;(&quot; Expressao &quot;)&quot;</a> | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Read.java">&quot;read&quot; &quot;(&quot; Id &quot;)&quot;</a>
    
    ChamadaProcedimento ::= { Decorator } &quot;call&quot; Id &quot;(&quot; [ <a href="../plp/Imperativa2/src/li2/plp/imperative2/command/ListaExpressao.java">ListaExpressao</a> ] &quot;)&quot;&nbsp;

    Decorator ::= &quot;@&quot; Id
  
    ListaExpressao ::= Expressao | Expressao, ListaExpressao
</pre>

## O que tem de novo?

<pre>
    Comando ::= <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Atribuicao.java">Atribuicao</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/ComandoDeclaracao.java">ComandoDeclaracao</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/While.java">While</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/IfThenElse.java">IfThenElse</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/IO.java">IO</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/SequenciaComando.java">Comando &quot;;&quot; Comando</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative1/command/Skip.java">Skip</a>
              | <a href="../plp/Imperativa2/src/li2/plp/imperative2/command/ChamadaProcedimento.java">ChamadaProcedimento</a>
              | Retorno

Retorno ::= &quot;return&quot; Expressao

Expressao ::= <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/Valor.java">Valor</a> 
                | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpUnaria.java">ExpUnaria</a> 
                | <a href="../plp/Imperativa2/src/li2/plp/expressions2/expression/ExpBinaria.java">ExpBinaria</a> 
                | Id "(" [ ListaExpressao ] ")"    // AJUSTAR ISSO, TA ESQUISITO SER EXPRESSAO
                | Id

DeclaracaoProcedimento ::= <a href="../plp/Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java">&quot;proc&quot; Tipo? Id &quot;(&quot; [ ListaDeclaracaoParametro ] &quot;)&quot; &quot;{&quot; Comando &quot;}&quot;</a>

Tipo ::= TipoPrimitivo
       | TipoSubAlgoritmo

TipoPrimitivo ::= "string" | "int" | "boolean" | "void"

TipoSubAlgoritmo ::= "func" Id "(" [ ListaTipos ] ")" "->" Tipo //adjust

ListaTipos ::= Tipo | Tipo "," ListaTipos

ChamadaProcedimento ::= { Decorador } "call" Id "(" [ ListaExpressao ] ")" 

Decorador ::= "@" Id
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
