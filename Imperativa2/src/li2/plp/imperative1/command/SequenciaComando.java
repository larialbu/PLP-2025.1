package li2.plp.imperative1.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative2.memory.TiposRetornoIncompativeisException;

public class SequenciaComando implements Comando {

	private Comando comando1;
	private Comando comando2;

	public SequenciaComando(Comando comando1, Comando comando2) {
		this.comando1 = comando1;
		this.comando2 = comando2;
	}

	/**
	 * Executa os comandos sequencialmente.
	 * 
	 * @param ambiente
	 *            o ambiente de execu��o.
	 * 
	 * @return o ambiente depois de modificado pela execu��o dos comandos.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		return comando2.executar(comando1.executar(ambiente));
	}

	/**
	 * Realiza a verificacao de tipos dos comandos
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os comandos s�o bem tipados;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		return comando1.checaTipo(ambiente) && comando2.checaTipo(ambiente);
	}

	@Override
	public boolean contemReturn(){
		return comando1.contemReturn() || comando2.contemReturn();
	}

	@Override
	public Tipo getTipoRetorno(){
		Tipo tipo1 = comando1.getTipoRetorno();
		Tipo tipo2 = comando2.getTipoRetorno();

		if(tipo1 != null && tipo2 != null){
			if(tipo1.eIgual(tipo2)){
				return tipo1;
			} else {
				throw new TiposRetornoIncompativeisException(
					"Tipos de retorno diferentes: " + tipo1 + " e " + tipo2
				);
			}
		} else if(tipo1 != null){
			return tipo1;
		} else if(tipo2 != null){
			return tipo2;
		} else {
			return null;
		}
	}
}
