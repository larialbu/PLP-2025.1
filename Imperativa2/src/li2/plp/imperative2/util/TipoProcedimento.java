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


	@Override
	public boolean eIgual(Tipo tipo) {
		if (tipo instanceof TipoProcedimento) {
			TipoProcedimento tipoProc = (TipoProcedimento) tipo;
			return parametrosEquivalentes(this.tiposParametrosFormais, tipoProc.tiposParametrosFormais) &&
				this.tipoRetorno.eIgual(tipoProc.tipoRetorno);
		}
		if (tipo instanceof TipoSubAlgoritmo) {
			TipoSubAlgoritmo tipoFunc = (TipoSubAlgoritmo) tipo;
			return parametrosEquivalentes(this.tiposParametrosFormais, tipoFunc.getParametros().getTipos()) &&
				this.tipoRetorno.eIgual(tipoFunc.getRetorno());
		}
		return false;
	}

	private boolean parametrosEquivalentes(List<Tipo> a, List<Tipo> b) {
		boolean aVazia = (a == null || a.isEmpty());
		boolean bVazia = (b == null || b.isEmpty());

		// Lista vazia == lista com void
		if (aVazia && bVazia) {
			return true;
		}

		if (a.size() == 1 && a.get(0).eIgual(li2.plp.expressions1.util.TipoPrimitivo.VOID) && bVazia) {
			return true;
		}

		if (b.size() == 1 && b.get(0).eIgual(li2.plp.expressions1.util.TipoPrimitivo.VOID) && aVazia) {
			return true;
		}

		// Caso geral, comparar listas diretamente
		return a.equals(b);
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
