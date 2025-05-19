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
Decorators permitem modificar ou estender o comportamento de funções de forma modular e reutilizável. Com eles, é possível adicionar funcionalidades sem alterar diretamente o corpo da função.

## Escopo do Projeto
Estender a Linguagem Imperativa 2 apresentada na disciplina para que ela ofereça suporte à aplicação de decorators sobre definições de funções. O objetivo é permitir que funções sejam modificadas ou estendidas de forma modular, por meio da sintaxe @nomeDoDecorator na chamada da função.

Adicionalmente, para fazer isso foi necessário rearranjar toda a estrutura dos procedimentos para permitir que fosse opcional para eles retornar algum valor ou não. Os tipos retornáveis são os tipos primitivos já implementados anteriormente: int, boolean, string, e caso um procedimento não retorne nada, é entendido que se retorna void.

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
- @log é a forma declarativa de aplicar o decorator na função soma.
- O resultado é uma função com o mesmo nome e comportamento da original, mas com comportamento adicional sem alterar o corpo da função.
- Em python, a associação de decorators à declaração de funções tem a consequência de não permitir que o usuário consiga chamar a função soma com seu comportamento original, apenas a já decorada.

### Exemplo de como os decorators funcionariam na linguagem da disciplina:
Código:
```
{
    var x = 0, var resultado = 0,
    proc log(func f(int, int) -> int, int a, int b) {
        write("Chamando função com argumentos:");
        write(a);
        write(b);
        resultado := f(a, b);
        write("Resultado:");
        write(resultado)
    },
    proc int soma(int x, int y) {
        return x + y
    };
    @log call soma(3, 4)
}
```
Output:
```
sintaxe verificada com sucesso!
resultado = "Chamando função com argumentos:" 3 4 "Resultado:" 7
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
Na nossa linguagem, foi decidido que os decorators seriam associados à chamada das funções, permitindo aplicar o efeito do decorator apenas quando o desenvolvedor quiser, como mostra o código a seguir:

Chamada decorada:
```
@log call soma(3, 4);
```

Chamada não decorada:
```
call soma(3, 4);
```

Além disso, decorators encadeáveis podem ser definidos da seguinte forma, sendo executados em ordem da esquerda para a direita:
```
@log @validate call soma(3, 4);
```

## Gramática EBNF

<pre>
    <a href="Imperativa2/src/li2/plp/imperative2/Programa.java">Programa</a> ::= <a href="Imperativa2/src/li2/plp/imperative1/command/Comando.java">Comando</a>
    
    Comando ::= <a href="/Imperativa2/src/li2/plp/imperative1/command/Atribuicao.java">Atribuicao</a>
              | <a href="Imperativa2/src/li2/plp/imperative1/command/ComandoDeclaracao.java">ComandoDeclaracao</a>
              | <a href="Imperativa2/src/li2/plp/imperative1/command/While.java">While</a>
              | <a href="Imperativa2/src/li2/plp/imperative1/command/IfThenElse.java">IfThenElse</a>
              | <a href="Imperativa2/src/li2/plp/imperative1/command/IO.java">IO</a>
              | <a href="Imperativa2/src/li2/plp/imperative1/command/SequenciaComando.java">Comando &quot;;&quot; Comando</a>
              | <a href="Imperativa2/src/li2/plp/imperative1/command/Skip.java">Skip</a>
              | <a href="Imperativa2/src/li2/plp/imperative2/command/ChamadaProcedimento.java">ChamadaProcedimento</a>
              | <a href="Imperativa2/src/li2/plp/imperative2/command/ComandoReturn.java">Retorno</a>

    Retorno ::= &quot;return&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a>
    
    Skip ::= 
    
    Atribuicao ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a> &quot;:=&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a>
    
    Expressao ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/Valor.java">Valor</a> 
                | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpUnaria.java">ExpUnaria</a> 
                | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpBinaria.java">ExpBinaria</a> 
                | <a href="Imperativa2/src/li2/plp/imperative2/command/ChamadaFuncao.java">Id &quot;(&quot; [ ListaExpressao ] &quot;)&quot;</a> 
                | <a href="Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a> 
    
    Valor ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorConcreto.java">ValorConcreto</a>
   
    ValorConcreto ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorInteiro.java">ValorInteiro</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorBooleano.java">ValorBooleano</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorString.java">ValorString</a> | <a href="Imperativa2/src/li2/plp/imperative2/memory/ValorFuncao.java">ValorFuncao</a>
    
    ExpUnaria ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpMenos.java">&quot;-&quot; Expressao</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpNot.java">&quot;not&quot; Expressao</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpLength.java">&quot;length&quot; Expressao</a>
    
    ExpBinaria ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpSoma.java">Expressao &quot;+&quot; Expressao</a>
                 | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpSub.java">Expressao &quot;-&quot; Expressao</a>
                 | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpAnd.java">Expressao &quot;and&quot; Expressao</a>
                 | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpOr.java">Expressao &quot;or&quot; Expressao</a>
                 | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpEquals.java">Expressao &quot;==&quot; Expressao</a>
                 | <a href="Imperativa2/src/li2/plp/expressions2/expression/ExpConcat.java">Expressao &quot;++&quot; Expressao</a>
    
    ComandoDeclaracao :: = &quot;{&quot; <a href="Imperativa2/src/li2/plp/imperative1/declaration/Declaracao.java">Declaracao</a> &quot;;&quot; <a href="Imperativa2/src/li2/plp/imperative1/command/Comando.java">Comando</a> &quot;}&quot;
    
    Declaracao ::= <a href="Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoVariavel.java">DeclaracaoVariavel</a>
                 | <a href="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java">DeclaracaoProcedimento</a>
                 | <a href="Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoComposta.java">DeclaracaoComposta</a>
    
    DeclaracaoVariavel ::= &quot;var&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a>  &quot;=&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a>&nbsp;
  
    DeclaracaoComposta ::= <a href="Imperativa2/src/li2/plp/imperative1/declaration/Declaracao.java">Declaracao</a> &quot;,&quot; <a href="Imperativa2/src/li2/plp/imperative1/declaration/Declaracao.java">Declaracao</a>
  
    DeclaracaoProcedimento ::= <a href="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java">&quot;proc&quot; Tipo? Id &quot;(&quot; [ ListaDeclaracaoParametro ] &quot;)&quot; &quot;{&quot; Comando &quot;}&quot;</a>
  
    ListaDeclaracaoParametro ::= <a href="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoParametro.java">Tipo Id</a> | <a href="Imperativa2/src/li2/plp/imperative2/declaration/ListaDeclaracaoParametro.java">Tipo Id &quot;,&quot; ListaDeclaracaoParametro</a>

    Tipo ::= <a href="Imperativa2/src/li2/plp/expressions1/util/TipoPrimitivo.java">TipoPrimitivo</a> | <a href="Imperativa2/src/li2/plp/imperative2/declaration/TipoSubAlgoritmo.java">TipoSubAlgoritmo</a>
    
    TipoPrimitivo ::= &quot;string&quot; | &quot;int&quot; | &quot;boolean&quot; | &quot;void&quot;

    TipoSubAlgoritmo ::= &quot;func&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a>  &quot;(&quot; [ <a href="Imperativa2/src/li2/plp/imperative2/declaration/ListaTipos.java">ListaTipos</a> ] &quot;)&quot; &quot;->&quot; <a href="Imperativa2/src/li2/plp/expressions1/util/TipoPrimitivo.java">TipoPrimitivo</a>

    ListaTipos ::= <a href="Imperativa2/src/li2/plp/expressions1/util/Tipo.java">Tipo</a> | <a href="Imperativa2/src/li2/plp/expressions1/util/Tipo.java">Tipo</a> &quot;,&quot; <a href="Imperativa2/src/li2/plp/imperative2/declaration/ListaTipos.java">ListaTipos</a>
    
    While ::= &quot;while&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a> &quot;do&quot; <a href="Imperativa2/src/li2/plp/imperative1/command/Comando.java">Comando</a>
  
    IfThenElse ::= &quot;if&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a> &quot;then&quot; <a href="Imperativa2/src/li2/plp/imperative1/command/Comando.java">Comando</a> &quot;else&quot; <a href="Imperativa2/src/li2/plp/imperative1/command/Comando.java">Comando</a>
  
    IO ::= <a href="Imperativa2/src/li2/plp/imperative1/command/Write.java">&quot;write&quot; &quot;(&quot; Expressao &quot;)&quot;</a> | <a href="Imperativa2/src/li2/plp/imperative1/command/Read.java">&quot;read&quot; &quot;(&quot; Id &quot;)&quot;</a>
    
    ChamadaProcedimento ::= { <a href="Imperativa2/src/li2/plp/imperative2/command/ChamadaProcedimento.java">Decorator</a> } &quot;call&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a> &quot;(&quot; [ <a href="Imperativa2/src/li2/plp/imperative2/command/ListaExpressao.java">ListaExpressao</a> ] &quot;)&quot;&nbsp;

    Decorator ::= &quot;@&quot; <a href="Imperativa2/src/li2/plp/expressions2/expression/Id.java">Id</a> 
  
    ListaExpressao ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java">Expressao</a> &quot;,&quot; <a href="Imperativa2/src/li2/plp/imperative2/command/ListaExpressao.java">ListaExpressao</a>
</pre>

## O que tem de novo?

<pre>
Comando ::= ... | Retorno

Retorno ::= &quot;return&quot; Expressao

ValorConcreto ::= <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorInteiro.java">ValorInteiro</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorBooleano.java">ValorBooleano</a> | <a href="Imperativa2/src/li2/plp/expressions2/expression/ValorString.java">ValorString</a> | <a href="Imperativa2/src/li2/plp/imperative2/memory/ValorFuncao.java">ValorFuncao</a>

Expressao ::= ... | Id &quot;(&quot; [ <a href="Imperativa2/src/li2/plp/imperative2/command/ListaExpressao.java">ListaExpressao</a> ] &quot;)&quot; 

DeclaracaoProcedimento ::= <a href="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java">&quot;proc&quot; Tipo? Id &quot;(&quot; [ ListaDeclaracaoParametro ] &quot;)&quot; &quot;{&quot; Comando &quot;}&quot;</a>

Tipo ::= TipoPrimitivo | TipoSubAlgoritmo

TipoPrimitivo ::= "string" | "int" | "boolean" | "void"

TipoSubAlgoritmo ::= "func" Id "(" [ ListaTipos ] ")" "->" TipoPrimitivo

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
