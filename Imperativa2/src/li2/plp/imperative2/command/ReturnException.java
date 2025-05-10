package li2.plp.imperative2.command;

import li2.plp.expressions2.expression.Valor;

public class ReturnException extends RuntimeException {
    private Valor valorRetornado;

    public ReturnException(Valor valorRetornado){
        this.valorRetornado = valorRetornado;
    }

    public Valor getValor() {
        return valorRetornado;
    }
}