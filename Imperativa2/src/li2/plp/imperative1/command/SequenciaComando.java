package li2.plp.imperative1.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
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
		System.out.println("EXECUTAR: SEQUENCIACOMANDO");
		System.out.println("COMANDO 1: " + comando1 + " COMANDO 2: " + comando2);
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
	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa amb){
		System.out.println("ENTROU NO GETTIPORETORNO DE SEQUENCIACOMANDO");
		Tipo tipo1 = comando1.getTipoRetorno(amb);
		Tipo tipo2 = comando2.getTipoRetorno(amb);

		if(tipo1 != TipoPrimitivo.VOID && tipo2 != TipoPrimitivo.VOID){
			if(tipo1.eIgual(tipo2)){
				System.out.println("SAIU DO GETTIPORETORNO DE SEQUENCIACOMANDO: " + tipo1);
				return tipo1;
			} else {
				throw new TiposRetornoIncompativeisException(
					"Tipos de retorno diferentes: " + tipo1 + " e " + tipo2
				);
			}
		} else if(tipo1 != TipoPrimitivo.VOID){
			System.out.println("SAIU DO GETTIPORETORNO DE SEQUENCIACOMANDO: " + tipo1);
			return tipo1;
		} else if(tipo2 != TipoPrimitivo.VOID){
			System.out.println("SAIU DO GETTIPORETORNO DE SEQUENCIACOMANDO: " + tipo2);
			return tipo2;
		} else {
			return TipoPrimitivo.VOID;
		}
	}
}
