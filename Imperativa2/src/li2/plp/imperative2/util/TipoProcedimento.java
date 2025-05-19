package li2.plp.imperative2.util;

import static li2.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import li2.plp.imperative2.declaration.TipoSubAlgoritmo;
import li2.plp.expressions1.util.Tipo;

public class TipoProcedimento implements Tipo {

	private List<Tipo> tiposParametrosFormais = new ArrayList<Tipo>();

	private Tipo tipoRetorno;

	public TipoProcedimento(List<Tipo> tiposParametrosFormais2, Tipo tipoRetorno) {
		this.tiposParametrosFormais.addAll(tiposParametrosFormais2);
		this.tipoRetorno = tipoRetorno;
	}

	public boolean eBooleano() {
		return false;
	}


	public boolean eIgual(Tipo tipo) {
		if (tipo instanceof TipoProcedimento) {
			TipoProcedimento tipoProc = (TipoProcedimento) tipo;
			return tipoProc.tiposParametrosFormais
					.equals(this.tiposParametrosFormais);
		}
        if (tipo instanceof TipoSubAlgoritmo) {
            TipoSubAlgoritmo tipoFunc = (TipoSubAlgoritmo) tipo;
            return tiposParametrosFormais.equals(tipoFunc.getParametros().getTipos()) &&
                   tipoRetorno.eIgual(tipoFunc.getRetorno());
        }
        return false;
	}

	public boolean eInteiro() {
		return false;
	}

	public boolean eString() {
		return false;
	}

	public boolean eValido() {
		boolean retorno = true;
		for (Tipo tipo : tiposParametrosFormais) {
			retorno &= tipo.eValido();
		}

		return retorno;
	}

	public String getNome() {
		return listToString(this.tiposParametrosFormais, "{", "}", ",");
	}

	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return this;
		else
			return null;
	}

	public Tipo getTipoRetorno(){
		return tipoRetorno;
	}

	public String toString(){
		return "func(" + tiposParametrosFormais + ") -> " + tipoRetorno;
	}

	public List<Tipo> getListaParametros(){
		return this.tiposParametrosFormais;
	}
}
