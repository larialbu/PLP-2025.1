package li2.plp.imperative1.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;

;

public class Skip implements Comando {

	/**
	 * N�o realiza nenhuma altera��o no ambiente.
	 * 
	 * @param ambiente
	 *            o ambiente de execu��o.
	 * 
	 * @return o ambiente inalterado.
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente) {
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos do comando
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se o comando � bem tipado; <code>false</code>
	 *         caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente) {
		return true;
	}

		@Override
	public boolean contemReturn(){
		return false;
	}

	@Override
	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa amb){
		System.out.println("ENTROU EM SKIP");
		return null;
	}
}
