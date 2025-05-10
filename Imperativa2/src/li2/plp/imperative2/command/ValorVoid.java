package li2.plp.imperative2.command;

import li2.plp.expressions2.expression.Valor;

public class ValorVoid implements Valor {

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ValorVoid;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public Valor clone() {
        return new ValorVoid();
    }
}
