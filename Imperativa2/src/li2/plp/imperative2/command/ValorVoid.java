package li2.plp.imperative2.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Valor;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.AmbienteCompilacao;

public class ValorVoid implements Valor {

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) {
    return this;
    }

    @Override
    public Valor reduzir(AmbienteExecucao ambiente) {
        // Não há redução para ValorVoid, retorna ele mesmo
        return this;
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao ambiente) {
        return true;  // Considera sempre válido.
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente) {
        // Retorna o tipo "void"
        return null;
    }

    @Override
    public ValorVoid clone() {
        return this;
    }
}
