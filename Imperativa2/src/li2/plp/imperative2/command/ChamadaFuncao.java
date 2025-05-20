package li2.plp.imperative2.command;

import java.util.List;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.imperative2.declaration.TipoSubAlgoritmo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.expression.Valor;
import li2.plp.imperative2.memory.ValorFuncao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.DeclaracaoParametro;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;
import li2.plp.imperative2.declaration.ListaTipos;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.imperative2.util.TipoProcedimento;

import li2.plp.imperative2.command.ReturnException;
import li2.plp.expressions2.expression.ValorVoid;

public class ChamadaFuncao implements Expressao {

    private Id nomeProcedimento;
    private ListaExpressao parametrosReais;

    public ChamadaFuncao(Id nomeProcedimento, ListaExpressao parametrosReais) {
        this.nomeProcedimento = nomeProcedimento;
        this.parametrosReais = parametrosReais;
        System.out.println("CONSTRUTOR DE CHAMADAFUNCAO");
    }

@Override
public Valor avaliar(AmbienteExecucao ambiente) {
    System.out.println("ENTROU NO AVALIAR DE CHAMADAFUNCAO");

    try {
        AmbienteExecucaoImperativa2 aux = (AmbienteExecucaoImperativa2) ambiente;

        DefProcedimento procedimento;

        try {
            // Primeiro, tenta obter como variável local
            Valor possivelFuncao = aux.get(nomeProcedimento);

            if (!(possivelFuncao instanceof ValorFuncao)) {
                throw new RuntimeException("Identificador '" + nomeProcedimento + "' não é uma função.");
            }

            procedimento = ((ValorFuncao) possivelFuncao).getValor();
            System.out.println("FUNÇÃO '" + nomeProcedimento + "' ENCONTRADA COMO VARIÁVEL (ARGUMENTO)");
        } catch (IdentificadorNaoDeclaradoException e) {
            // Se não existir como variável, tenta como procedimento global
            procedimento = aux.getProcedimento(nomeProcedimento);
            System.out.println("FUNÇÃO '" + nomeProcedimento + "' ENCONTRADA COMO PROCEDIMENTO GLOBAL");
        }

        if (!procedimento.retornaValor()) {
            throw new RuntimeException("Procedimento '" + nomeProcedimento + "' não retorna valor.");
        }

        // ✅ NOVO TRECHO — trata [void] como chamada sem argumentos reais
        ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();
        boolean formaisSaoVoid = parametrosFormais != null &&
                                 parametrosFormais.getTipos().size() == 1 &&
                                 parametrosFormais.getTipos().get(0).eIgual(TipoPrimitivo.VOID);

        ListaValor valoresReais;
        if ((parametrosReais == null || parametrosReais.length() == 0) && formaisSaoVoid) {
            valoresReais = new ListaValor(); // chamada vazia
        } else {
            valoresReais = parametrosReais.avaliar(aux);
        }

        return avaliarComValores(aux, procedimento, valoresReais);

    } catch (Exception e) {
        System.out.println("SAIU (EXCEPTION) DO AVALIAR DE CHAMADAFUNCAO");
        throw new RuntimeException("Erro durante a avaliação da função: " + e.getMessage(), e);
    }
}

    public Valor avaliarComValores(AmbienteExecucaoImperativa2 ambiente, DefProcedimento procedimento, ListaValor valoresReais) 
        throws IdentificadorNaoDeclaradoException, IdentificadorJaDeclaradoException, EntradaVaziaException, ErroTipoEntradaException{

        ambiente.incrementa();

        ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();
        ambiente = bindParameters(ambiente, parametrosFormais, valoresReais);

        Valor retorno;
        try {
            ambiente = (AmbienteExecucaoImperativa2) procedimento.getComando().executar(ambiente);
            retorno = null; // Valor default se não tiver return
        } catch (ReturnException e) {
            retorno = e.getValor();
        } finally {
            ambiente.restaura();
        }

        System.out.println("SAIU DO AVALIAR DE CHAMADAFUNCAO");
        return retorno;
    }

    private AmbienteExecucaoImperativa2 bindParameters(
            AmbienteExecucaoImperativa2 ambiente,
            ListaDeclaracaoParametro parametrosFormais) throws Exception {
        ListaValor listaValor = parametrosReais.avaliar(ambiente);
        System.out.println("ENTROU NO BINDPARAMETERS DE CHAMADAFUNCAO");
        while (listaValor.length() > 0) {
            ambiente.map(parametrosFormais.getHead().getId(), listaValor.getHead());
            parametrosFormais = (ListaDeclaracaoParametro) parametrosFormais.getTail();
            listaValor = (ListaValor) listaValor.getTail();
        }
        System.out.println("SAIU DO BINDPARAMETERS DE CHAMADAFUNCAO");
        return ambiente;
    }

	private AmbienteExecucaoImperativa2 bindParameters(
			AmbienteExecucaoImperativa2 ambiente,
			ListaDeclaracaoParametro parametrosFormais,
			ListaValor listaValor) throws VariavelJaDeclaradaException {
		System.out.println("ENTROU NO BINDPARAMETERS PERSONALIZADO");
		while (listaValor.length() > 0) {
			System.out.println("ID: " + parametrosFormais.getHead().getId() + " VALOR: " + listaValor.getHead());
			ambiente.map(parametrosFormais.getHead().getId(), listaValor.getHead());
			parametrosFormais = (ListaDeclaracaoParametro) parametrosFormais.getTail();
			listaValor = (ListaValor) listaValor.getTail();
		}
		System.out.println("SAIU DO BINDPARAMETERS PERSONALIZADO");
		return ambiente;
	}

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) {
        try {
            System.out.println("ENTROU NO CHECATIPO DE CHAMADAFUNCAO");

            AmbienteCompilacaoImperativa aux = (AmbienteCompilacaoImperativa) ambiente;
            TipoSubAlgoritmo tipoFuncao;
            
            try {
                tipoFuncao = aux.getFuncao(nomeProcedimento);
                System.out.println("FUNCAO " + nomeProcedimento + " ENCONTRADA NAS VARIAVEIS");
            } catch (VariavelNaoDeclaradaException e1) {
                try {
                    DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);
                    tipoFuncao = (TipoSubAlgoritmo) procedimento.getTipo();
                    System.out.println("FUNCAO " + nomeProcedimento + " ENCONTRADA COMO PROCEDIMENTO");
                } catch (IdentificadorNaoDeclaradoException e2) {
                    System.out.println("FUNCAO " + nomeProcedimento + " NAO ENCONTRADA NO AMBIENTE");
                    return false;
                }
            }

            if (tipoFuncao == null || !tipoFuncao.eValido()) {
                System.out.println("TIPO INVALIDO PARA A FUNCAO " + nomeProcedimento);
                return false;
            }

            ListaDeclaracaoParametro parametrosFormais = converteTiposParaDeclaracao(tipoFuncao.getParametros());

            System.out.println("PARAMETROSFORMAIS: " + parametrosFormais);
            boolean parametrosOk;

            boolean formaisVazios = (parametrosFormais == null || parametrosFormais.getTipos().isEmpty());
            boolean reaisVazios = (parametrosReais == null || parametrosReais.length() == 0);

            // Tratamento especial: função declarada como ([void]) e chamada com zero argumentos
            if (!formaisVazios && reaisVazios && parametrosFormais.getTipos().size() == 1 
                && parametrosFormais.getTipos().get(0).eIgual(TipoPrimitivo.VOID)) {
                parametrosOk = true;
            } else if (formaisVazios && reaisVazios) {
                parametrosOk = true;
            } else if (!formaisVazios && !reaisVazios) {
                parametrosOk = parametrosReais.checaTipo(ambiente, parametrosFormais);
            } else {
                parametrosOk = false;
            }

            boolean retornaValor = !tipoFuncao.getRetorno().eIgual(TipoPrimitivo.VOID);

            System.out.println("SAIU DO CHECATIPO DE CHAMADAFUNCAO: " + parametrosOk + " " + retornaValor);
            return parametrosOk && retornaValor;

        } catch (Exception e) {
            System.out.println("ERRO AO VERIFICAR TIPO NA CHAMADA DA FUNCAO");
            return false;
        }
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente) {
        try {
            System.out.println("ENTROU NO GETTIPPO DE CHAMADAFUNCAO");

            AmbienteCompilacaoImperativa aux = (AmbienteCompilacaoImperativa) ambiente;

            Tipo tipo;

            // Tenta obter como variável (caso seja função passada como parâmetro)
            try {
                tipo = aux.getFuncao(nomeProcedimento);  // Corrigido para passar o nome como String
                System.out.println("FUNCAO " + nomeProcedimento + " ENCONTRADA COMO VARIAVEL: " + tipo);
            } catch (IdentificadorNaoDeclaradoException e) {
                // Se não for uma variável, tenta obter como procedimento global
                DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);  // Corrigido para passar o nome como String
                tipo = procedimento.getTipoRetorno(aux);
                System.out.println("FUNCAO " + nomeProcedimento + " ENCONTRADA COMO PROCEDIMENTO: " + tipo);
            }

            // Verifica se o tipo retornado é válido para função
            if (tipo instanceof TipoSubAlgoritmo) {
                TipoSubAlgoritmo ts = (TipoSubAlgoritmo) tipo;
                return ts.getTipoRetorno();
            } else {
                // Caso contrário, retorna o tipo diretamente (para procedimentos)
                return tipo;
            }

        } catch (Exception e) {
            System.err.println("ERRO EM GETTIPO DE CHAMADAFUNCAO: " + e.getMessage());
            throw new IdentificadorNaoDeclaradoException(nomeProcedimento.toString());
        }
    }

    @Override
    public Expressao reduzir(AmbienteExecucao ambiente) {
        System.out.println("REDUZIR DE CHAMADAFUNCAO");
        // Para simplificação, pode retornar a própria expressão se não for possível reduzir.
        return this;
    }

    @Override
    public Expressao clone() {
        System.out.println("CLONE DE CHAMADAFUNCAO");
        return new ChamadaFuncao(this.nomeProcedimento, this.parametrosReais);
    }

    private static ListaDeclaracaoParametro converteTiposParaDeclaracao(ListaTipos listaTipos) {
        ListaDeclaracaoParametro resultado = null;

        List<Tipo> tipos = listaTipos.getTipos();
        int index = tipos.size();

        // Inverte a ordem ao inserir, para manter a ordem original
        for (int i = tipos.size() - 1; i >= 0; i--) {
            Tipo tipo = tipos.get(i);
            DeclaracaoParametro parametro = new DeclaracaoParametro(new Id("param" + index), tipo);
            resultado = new ListaDeclaracaoParametro(parametro, resultado);
            index--;
        }

        return resultado;
    }
}
