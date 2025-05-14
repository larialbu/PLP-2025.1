package li2.plp.imperative2.memory;

import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.Contexto;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.imperative1.memory.ContextoCompilacaoImperativa;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative2.declaration.DefProcedimento;

public class ContextoCompilacaoImperativa2 extends ContextoCompilacaoImperativa
        implements AmbienteCompilacaoImperativa2 {

    private Contexto<DefProcedimento> contextoProcedimentos;

    public ContextoCompilacaoImperativa2(ListaValor entrada) {
        super(entrada);
        contextoProcedimentos = new Contexto<>();
    }

    @Override
    public void incrementa() {
        super.incrementa();
        this.contextoProcedimentos.incrementa();
    }

    @Override
    public void restaura() {
        super.restaura();
        this.contextoProcedimentos.restaura();
    }

    public void mapProcedimento(Id idArg, DefProcedimento procedimentoId)
            throws ProcedimentoJaDeclaradoException {
        try {
            System.out.println("ENTROU NO MAPPROCEDIMENTO DO AMBIENTECOMPILACAOIMPERATIVA2");
            this.contextoProcedimentos.map(idArg, procedimentoId);
        } catch (VariavelJaDeclaradaException e) {
            throw new ProcedimentoJaDeclaradoException(idArg);
        }
    }

    public DefProcedimento getProcedimento(Id idArg)
            throws ProcedimentoNaoDeclaradoException {
        try {
            System.out.println("ENTROU NO GETPROCEDIMENTO DO AMBIENTECOMPILACAOIMPERATIVA2");
            return this.contextoProcedimentos.get(idArg);
        } catch (VariavelNaoDeclaradaException e) {
            throw new ProcedimentoNaoDeclaradoException(idArg);
        }
    }
}
