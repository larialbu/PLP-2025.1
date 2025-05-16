package li2.plp.imperative2.command;

import java.util.LinkedList;
import java.util.List;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative1.util.Lista;
import li2.plp.imperative2.declaration.DeclaracaoParametro;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;

public class ListaExpressao extends Lista<Expressao> {

	public ListaExpressao() {

	}

	public ListaExpressao(Expressao expressao) {
		super(expressao, new ListaExpressao());
	}

	public ListaExpressao(Expressao expressao, ListaExpressao listaExpressao) {
		super(expressao, listaExpressao);
	}

	public ListaValor avaliar(AmbienteExecucaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		System.out.println("ENTROU NO AVALIAR DE LISTAEXPRESSAO");
		if (length() >= 2)
			return new ListaValor(getHead().avaliar(ambiente),
					((ListaExpressao) getTail()).avaliar(ambiente));
		else if (length() == 1)
			return new ListaValor(getHead().avaliar(ambiente));
		else
			return new ListaValor();
	}

	public List<Tipo> getTipos(AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		List<Tipo> result = new LinkedList<Tipo>();
		System.out.println("ENTROU NO GETTIPOS DE LISTAEXPRESSAO");
		if (this.length() >= 2) {
			System.out.println(result);
			result.add(getHead().getTipo(ambiente));
			System.out.println(result);
			result.addAll(((ListaExpressao) getTail()).getTipos(ambiente));
			System.out.println(result);
		} else if (length() == 1) {
			System.out.println(result + "else");
			result.add(getHead().getTipo(ambiente));
		}
		System.out.println(result + "return");
		System.out.println("SAIU DO GETTIPOS DE LISTAEXPRESSAO");
		return result;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente, ListaDeclaracaoParametro parametrosFormais) 
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		List<Tipo> tiposReais = this.getTipos((AmbienteCompilacaoImperativa) ambiente);
		System.out.println("ENTROU NO CHECATIPO DE LISTAEXPRESSAO");
		
		if(tiposReais.size() != parametrosFormais.length()){
			return false;
		}

		ListaDeclaracaoParametro params = parametrosFormais;

		for(int i=0; i<tiposReais.size(); i++){
			Tipo tipoReal = tiposReais.get(i);
			Tipo tipoFormal = params.getHead().getTipo();

			if(!tipoReal.eIgual(tipoFormal)){
				return false;
			}
			
			params = (ListaDeclaracaoParametro) params.getTail();
		}

		return true;
	}
}
