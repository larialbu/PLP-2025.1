# Projeto da Disciplina IN1007 - Paradigmas de Linguagem de Programação

Este repositório contém o projeto desenvolvido na disciplina **IN1007 - Paradigmas de Linguagem de Programação**, que faz parte do **Programa de Pós-Graduação em Ciência da Computação (PPGCC)** no **Centro de Informática da Universidade Federal de Pernambuco (CIn-UFPE)**.

## Integrantes do Projeto

- **Larissa Azevedo Marques Cavalcanti de Albuquerque** - Aluna de Mestrado do PPGCC
- **Pedro Henrique Lopes dos Santos** - Aluno de Mestrado do PPGCC

## Sobre a Disciplina
A disciplina IN1007 explora diferentes paradigmas de programação, incluindo:
- Paradigma Imperativo
- Paradigma Funcional
- Paradigma Orientado a Objetos
- Paradigma Lógico

Mais informações sobre a disciplina podem ser encontradas no site oficial: [IN1007 - UFPE](https://www.cin.ufpe.br/~in1007/).

## Descrição do Projeto

## Exceções e Tratamento de Erros
Este projeto implementa um sistema de **exceções** para tratar erros de forma estruturada em nossa linguagem. O objetivo é permitir que erros sejam lançados e capturados de maneira organizada, melhorando a robustez do código.

## Funcionalidades
- Lançamento de exceções com `throw "Erro"`
- Captura e tratamento de exceções com `try { ... } catch { ... }`
- Definição de uma **hierarquia de tipos de erro**
- (Opcional) Permitir exceções customizadas definidas pelo usuário

## Desafios Técnicos
- Criar um mecanismo eficiente para lançar exceções.
- Implementar um sistema de captura e tratamento que preserve a execução segura do código.
- Projetar uma estrutura de **hierarquia de exceções** bem organizada.
- Possibilitar a criação de **exceções personalizadas** pelo usuário (opcional).

## Exemplo de Uso
```C
try {
    throw "Erro: Divisão por zero";
} catch (e) {
    print("Exceção capturada: ", e);
}
```

## Como Executar


## Estrutura do Repositório
- `/src` - Código-fonte do projeto
- `/docs` - Documentação e relatórios
- `/tests` - Casos de teste

## Contribuição
Caso queira contribuir para o projeto, siga estas etapas:
1. Faça um fork do repositório
2. Crie uma branch para sua contribuição (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adicionando nova feature'`)
4. Envie para o repositório remoto (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença
(Especifique a licença do projeto, se houver.)
