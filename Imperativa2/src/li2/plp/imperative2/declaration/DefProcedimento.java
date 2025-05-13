package li2.plp.imperative2.declaration;

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

	private Tipo tipoRetorno = TipoPrimitivo.VOID;

	/**
	 * Construtor
	 * 
	 * @param parametrosFormais
	 *            Declara��o de ListaDeclaracaoParametro
	 * @param comando
	 *            Declara�ao de Comando.
	 */
	public DefProcedimento(ListaDeclaracaoParametro parametrosFormais,
			Comando comando) {
		this.parametrosFormais = parametrosFormais;
		this.comando = comando;

		this.tipoRetorno = null;
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

	public void setTipoRetorno(Tipo tipo){
		if(tipo != null){
			this.tipoRetorno = tipo;
		}
	}

	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa amb) {
		if(comando.contemReturn()){
			System.out.println("ENTROU NO IF DE DEFPROCEDIMENTO");
			return comando.getTipoRetorno(amb);
		}
		System.out.println("NAO ENTROU NO IF DE DEFPROCEDIMENTO");
        return null;
    }

	public Tipo getTipo() {
		return new TipoProcedimento(parametrosFormais.getTipos());
	}
}
