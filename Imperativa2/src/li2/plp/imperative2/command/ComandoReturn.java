package li2.plp.imperative2.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative2.memory.ErroTipoRetornoException;

public class ComandoReturn implements Comando{

    private Expressao expressao;

    public ComandoReturn(Expressao expressao){
        this.expressao = expressao;
        System.out.println("ENTROU NO CONSTRUTOR DE COMANDORETURN");
    }

    @Override
    public AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa amb)
            throws IdentificadorNaoDeclaradoException, IdentificadorJaDeclaradoException,
                   EntradaVaziaException, ErroTipoEntradaException {
        //lança uma exceção especial para "interromper" e retornar o valor.
        System.out.println("ENTROU NO EXECUTAR DE COMANDORETURN");
        throw new ReturnException(expressao.avaliar(amb));
    }

    @Override
    public boolean checaTipo(li2.plp.imperative1.memory.AmbienteCompilacaoImperativa amb)
            throws IdentificadorNaoDeclaradoException, IdentificadorJaDeclaradoException {
        System.out.println("ENTROU NO CHECATIPO DE COMANDORETURN");
        return expressao.checaTipo(amb);
    }

    @Override
    public boolean contemReturn(){
        System.out.println("ENTROU NO CONTEMRETURN DE COMANDORETURN");
        return true;
    }

    @Override
    public Tipo getTipoRetorno(li2.plp.imperative1.memory.AmbienteCompilacaoImperativa amb){
        System.out.println("ENTROU NO GETTIPORETORNO DE COMANDORETURN");
        if(expressao != null){
            try{
                System.out.println("RETORNO DO TIPO: " + expressao.getTipo(amb));
                return expressao.getTipo(amb);
            } catch (Exception e){
                throw new ErroTipoRetornoException();
            }
        }
        throw new ErroTipoRetornoException("Proc tipo void, nenhum retorno esperado");
    }
}