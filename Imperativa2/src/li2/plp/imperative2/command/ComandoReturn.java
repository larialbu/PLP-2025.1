package li2.plp.imperative2.command;

import li2.plp.expressions2.expression.Expressao;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;

public class ComandoReturn implements Comando{

    private Expressao expressao;

    public ComandoReturn(Expressao expressao){
        this.expressao = expressao;
    }

    @Override
    public AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa amb)
            throws IdentificadorNaoDeclaradoException, IdentificadorJaDeclaradoException,
                   EntradaVaziaException, ErroTipoEntradaException {
        //lança uma exceção especial para "interromper" e retornar o valor.
        throw new ReturnException(expressao.avaliar(amb));
    }

    @Override
    public boolean checaTipo(li2.plp.imperative1.memory.AmbienteCompilacaoImperativa amb)
            throws IdentificadorNaoDeclaradoException, IdentificadorJaDeclaradoException {
        return expressao.checaTipo(amb);
    }
}