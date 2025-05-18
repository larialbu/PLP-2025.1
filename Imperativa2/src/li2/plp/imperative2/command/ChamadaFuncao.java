package li2.plp.imperative2.command;

import java.util.List;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.imperative2.declaration.TipoSubAlgoritmo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.expression.Valor;
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
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

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
            DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);
            if(!procedimento.retornaValor()){
                throw new RuntimeException("Procedimento '" + nomeProcedimento + "' não retorna valor.");
            }

            aux.incrementa();
            ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();
            aux = bindParameters(aux, parametrosFormais);

            Valor retorno;
            try {
                aux = (AmbienteExecucaoImperativa2) procedimento.getComando().executar(aux);
                retorno = new ValorVoid();  // Caso não tenha return explícito
            } catch (ReturnException e) {
                retorno = e.getValor();
            } finally {
                aux.restaura();
            }

            System.out.println("SAIU DO AVALIAR DE CHAMADAFUNCAO");
            return retorno;

        } catch (Exception e) {
            System.out.println("SAIU (EXCEPTION) DO AVALIAR DE CHAMADAFUNCAO");
            throw new RuntimeException("Erro durante a avaliação da função: " + e.getMessage(), e);
        }
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

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) {
        try {
            System.out.println("ENTROU NO CHECATIPO DE CHAMADAFUNCAO");

            AmbienteCompilacaoImperativa aux = (AmbienteCompilacaoImperativa) ambiente;
            TipoSubAlgoritmo tipoFuncao;
            
            try{
                tipoFuncao = aux.getFuncao(nomeProcedimento);
                System.out.println("FUNCAO " + nomeProcedimento + " ENCONTRADA NAS VARIAVEIS");
            } catch (VariavelNaoDeclaradaException e1) {
                try{
                    DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);
                    tipoFuncao = (TipoSubAlgoritmo) procedimento.getTipo();
                    System.out.println("FUNCAO " + nomeProcedimento + " ENCONTRADA COMO PROCEDIMENTO");
                } catch (IdentificadorNaoDeclaradoException e2){
                    System.out.println("FUNCAO " + nomeProcedimento + " NAO ENCONTRADA NO AMBIENTE");
                    return false;
                }
            }

            if(tipoFuncao == null || !tipoFuncao.eValido()){
                System.out.println("TIPO INVALIDO PARA A FUNCAO " + nomeProcedimento);
                return false;
            }
            
            ListaDeclaracaoParametro parametrosFormais = converteTiposParaDeclaracao(tipoFuncao.getParametros());

            System.out.println("PARAMETROSFORMAIS: " + parametrosFormais);
            boolean parametrosOk;

            boolean formaisVazios = (parametrosFormais == null || parametrosFormais.getTipos().isEmpty());
            boolean reaisVazios = (parametrosReais == null || parametrosReais.length() == 0);

            if (formaisVazios && reaisVazios) {
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
            DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);
            System.out.println("SAIU DO GETTIPO DE CHAMADAFUNCAO");
            return procedimento.getTipoRetorno(aux);
        } catch (Exception e) {
            return null;
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
