package li2.plp.imperative2.command;

import java.util.List;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.expression.Valor;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;

import li2.plp.imperative2.command.ReturnException;
import li2.plp.imperative2.command.ValorVoid;

public class ChamadaFuncao implements Expressao {
    private Id nomeProcedimento;
    private ListaExpressao parametrosReais;

    public ChamadaFuncao(Id nomeProcedimento, ListaExpressao parametrosReais) {
        this.nomeProcedimento = nomeProcedimento;
        this.parametrosReais = parametrosReais;
    }

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) {
        try {
            AmbienteExecucaoImperativa2 aux = (AmbienteExecucaoImperativa2) ambiente;
            DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);

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

            return retorno;

        } catch (Exception e) {
            throw new RuntimeException("Erro durante a avaliação da função: " + e.getMessage(), e);
        }
    }


    private AmbienteExecucaoImperativa2 bindParameters(
            AmbienteExecucaoImperativa2 ambiente,
            ListaDeclaracaoParametro parametrosFormais) throws Exception {
        ListaValor listaValor = parametrosReais.avaliar(ambiente);
        while (listaValor.length() > 0) {
            ambiente.map(parametrosFormais.getHead().getId(), listaValor.getHead());
            parametrosFormais = (ListaDeclaracaoParametro) parametrosFormais.getTail();
            listaValor = (ListaValor) listaValor.getTail();
        }
        return ambiente;
    }

    @Override
    public Expressao clone() {
        return new ChamadaFuncao(nomeProcedimento, parametrosReais);
    }
}
