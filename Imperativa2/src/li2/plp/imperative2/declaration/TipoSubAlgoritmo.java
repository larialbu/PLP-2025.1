package li2.plp.imperative2.declaration;

import li2.plp.imperative2.util.TipoProcedimento;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.imperative2.declaration.ListaTipos;

import java.util.List;

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
    public boolean eIgual(Tipo tipo) {
        if (tipo instanceof TipoProcedimento) {
            TipoProcedimento outro = (TipoProcedimento) tipo;
            return parametrosEquivalentes(this.parametros.getTipos(), outro.getListaParametros()) &&
                this.retorno.equals(outro.getTipoRetorno());
        }

        if (tipo instanceof TipoSubAlgoritmo) {
            TipoSubAlgoritmo outro = (TipoSubAlgoritmo) tipo;
            return parametrosEquivalentes(this.parametros.getTipos(), outro.parametros.getTipos()) &&
                this.retorno.equals(outro.retorno);
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

    public Tipo getTipoRetorno(){
        return retorno;
    }

    private boolean parametrosEquivalentes(List<Tipo> a, List<Tipo> b) {
        // Ambos são null ou vazios
        if ((a == null || a.size() == 0) && (b == null || b.size() == 0)) {
            return true;
        }

        // Um é [void], o outro é vazio: trata como equivalente
        if ((a.size() == 1 && a.get(0).eIgual(TipoPrimitivo.VOID) && (b == null || b.size() == 0)) ||
            (b.size() == 1 && b.get(0).eIgual(TipoPrimitivo.VOID) && (a == null || a.size() == 0))) {
            return true;
        }

        // Compara normalmente
        return a.equals(b);
    }
}