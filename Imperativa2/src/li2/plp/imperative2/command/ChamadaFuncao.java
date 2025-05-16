package li2.plp.imperative2.command;

import java.util.List;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.expression.Valor;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;

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
        System.out.println("ENTROU NO BINNDPARAMETERS DE CHAMADAFUNCAO");
        while (listaValor.length() > 0) {
            ambiente.map(parametrosFormais.getHead().getId(), listaValor.getHead());
            parametrosFormais = (ListaDeclaracaoParametro) parametrosFormais.getTail();
            listaValor = (ListaValor) listaValor.getTail();
        }
        System.out.println("SAIU DO BINNDPARAMETERS DE CHAMADAFUNCAO");
        return ambiente;
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) {
        try {
            // Realiza o cast para o ambiente de compilação específico
            System.out.println("ENTROU NO CHECATIPO DE CHAMADAFUNCAO");
            
            AmbienteCompilacaoImperativa aux = (AmbienteCompilacaoImperativa) ambiente;
            
            DefProcedimento procedimento = aux.getProcedimento(nomeProcedimento);
            
            ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();

            boolean parametrosOk = parametrosReais.checaTipo(ambiente, parametrosFormais);
            boolean retornaValor = !TipoPrimitivo.VOID.eIgual(procedimento.getTipoRetorno(aux));
            
            System.out.println("SAIU DO CHECATIPO DE CHAMADAFUNCAO");
            return parametrosOk && retornaValor;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente) {
        try {
            System.out.println("ENTROU NO GETTIPPO DE CHAMADAFUNCAO");
            // Realiza o cast para o ambiente de compilação específico
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
}
