package li2.plp.imperative2.declaration;

import li2.plp.expressions1.util.Tipo;
import li2.plp.imperative2.declaration.ListaTipos;

public class TipoSubAlgoritmo implements Tipo {
    private ListaTipos parametros;
    private Tipo retorno;

    public TipoSubAlgoritmo(ListaTipos parametros, Tipo retorno){
        this.parametros = parametros;
        this.retorno = retorno;
    }

    public ListaTipos getParametros(){
        return parametros;
    }

    public Tipo getRetorno(){
        return retorno;
    }
    
    @Override
    public boolean eIgual(Tipo outro) {
        if (outro instanceof TipoSubAlgoritmo) {
            TipoSubAlgoritmo outroFunc = (TipoSubAlgoritmo) outro;
            return parametros.eIgual(outroFunc.getParametros()) &&
                   retorno.eIgual(outroFunc.getRetorno());
        }
        return false;
    }

    @Override
    public boolean eValido() {
        return retorno != null && parametros != null;
    }

    @Override
    public Tipo intersecao(Tipo outroTipo) {
        if (outroTipo instanceof TipoSubAlgoritmo) {
            TipoSubAlgoritmo outroFunc = (TipoSubAlgoritmo) outroTipo;
            if (this.parametros.eIgual(outroFunc.getParametros()) &&
                this.retorno.eIgual(outroFunc.getRetorno())) {
                return this;
            }
        }
        return null;
    }

    @Override
    public String getNome() {
        return "func(" + parametros + ") -> " + retorno;
    }

    @Override
    public boolean eInteiro() {
        return false;
    }

    @Override
    public boolean eBooleano() {
        return false;
    }

    @Override
    public boolean eString() {
        return false;
    }

    @Override
    public String toString() {
        return getNome();
    }
}