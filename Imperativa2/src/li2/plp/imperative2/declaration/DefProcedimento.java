package li2.plp.imperative2.declaration;

import java.util.ArrayList;
import java.util.List;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative2.util.TipoProcedimento;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;

/**
 * Uma defini�ao de procedimento � uma declara�ao de comando e parametrosFormais
 * uma declara��o de procedimento.
 */
public class DefProcedimento {

	/**
	 * Declara��o dos parametrosFormais
	 */
	private ListaDeclaracaoParametro parametrosFormais;

	/**
	 * Declara�ao de Comando
	 */
	private Comando comando;

	private final Tipo tipoRetorno;

	/**
	 * Construtor
	 * 
	 * @param parametrosFormais
	 *            Declara��o de ListaDeclaracaoParametro
	 * @param comando
	 *            Declara�ao de Comando.
	 */
	public DefProcedimento(ListaDeclaracaoParametro parametrosFormais,
			Comando comando, Tipo tipoRetorno) {
		this.parametrosFormais = parametrosFormais;
		this.comando = comando;
		this.tipoRetorno = tipoRetorno;
	}

	/**
	 * Obt�m o comando do Procedimento.
	 * 
	 * @return o comando
	 */
	public Comando getComando() {
		return comando;
	}

	/**
	 * Obt�m os parametrosFormais do Procedimento.
	 * 
	 * @return a ListaDeclaracaoParametro
	 */
	public ListaDeclaracaoParametro getParametrosFormais() {
		return parametrosFormais;
	}

	public boolean retornaValor(){
		return !TipoPrimitivo.VOID.eIgual(this.tipoRetorno);
	}

	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa amb) {
		System.out.println("GETTIPORETORNO DE DEFPROCEDIMENTO");

        return this.tipoRetorno;
    }

	public Tipo getTipo() {
		System.out.println("ENTRANDO EM GETTIPO DO DEFPROCEDIMENTO: " + parametrosFormais.getTipos());
		List<Tipo> listaTipos = parametrosFormais.getTipos();

		if(retornaValor()){
			ListaTipos listaTiposFunc = new ListaTipos();

			for(Tipo tipo : listaTipos){
				listaTiposFunc.adicionar(tipo);
				System.out.println("TIPO DE PARAMETRO: " + tipo);
			}

			TipoSubAlgoritmo tipoFunc = new TipoSubAlgoritmo(listaTiposFunc, tipoRetorno);
			System.out.println("SAINDO DE GETTIPO DE DEFPROCEDIMENTO FUNC: " + tipoFunc);
			return tipoFunc;
		} else {
			TipoProcedimento tipoProc = new TipoProcedimento(listaTipos, TipoPrimitivo.VOID);
			System.out.println("SAINDO DE GETTIPO DE DEFPROCEDIMENTO PROC: " + tipoProc);
			return tipoProc;
		}
	}
}