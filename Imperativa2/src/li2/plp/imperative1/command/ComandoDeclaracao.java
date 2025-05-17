package li2.plp.imperative1.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative1.declaration.Declaracao;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;

public class ComandoDeclaracao implements Comando {

	private Declaracao declaracao;

	private Comando comando;

	public ComandoDeclaracao(Declaracao declaracao, Comando comando) {
		this.declaracao = declaracao;
		this.comando = comando;
		System.out.println("CONSTRUTOR DO COMANDODECLARACAO");
	}

	/**
	 * Declara a(s) vari�vel(is) e executa o comando.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return o ambiente modificado pela execu��o da declara��o e do comando.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		System.out.println("ENTROU NO EXECUTAR DO COMANDODECLARACAO");
		ambiente.incrementa();
		System.out.println(comando + " " + declaracao);
		ambiente = declaracao.elabora(ambiente);
		ambiente = comando.executar(ambiente);
		ambiente.restaura();
		System.out.println("SAIU DO EXECUTAR DO COMANDODECLARACAO");
		return ambiente;
	}

	/**
	 * Verifica se o tipo do comando esta correto, levando em conta que o tipo
	 * de uma variavel � o tipo do valor da sua primeira atribuicao.
	 */
	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
				IdentificadorNaoDeclaradoException, EntradaVaziaException {

		System.out.println("ENTROU NO CHECATIPO DE COMANDODECLARACAO");

		ambiente.incrementa();

		// 1. Mapeia a declaração antes de verificar tipos
		declaracao.mapa(ambiente);

		// 2. Checa tipos da declaração e do comando
		boolean decct = declaracao.checaTipo(ambiente);
		System.out.println("COMANDODECLARACAO (DEC): " + declaracao + ": " + decct);

		boolean comct = comando.checaTipo(ambiente);
		System.out.println("COMANDODECLARACAO (COM): " + comando + ": " + comct);

		ambiente.restaura();

		boolean resposta = decct && comct;
		System.out.println("SAIU DO CHECATIPO DO COMANDODECLARACAO: " + resposta);
		return resposta;
	}


	@Override
	public boolean contemReturn(){
		System.out.println("ENTROU NO CONTEMRETURN DO COMANDODECLARACAO");
		return comando.contemReturn();
	}

	@Override
	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa amb){
		System.out.println("ENTROU NO GETTIPORETORNO DO COMANDODECLARACAO");
		if(comando.contemReturn()){
			Tipo tipoRetorno = comando.getTipoRetorno(amb);

			if(tipoRetorno == TipoPrimitivo.VOID){
				System.out.println("SAIU DO GETTIPORETORNO DO COMANDODECLARACAO (VOID)");
				return TipoPrimitivo.VOID;
			}
			System.out.println("SAIU DO GETTIPORETORNO DO COMANDODECLARACAO");
			return tipoRetorno;
		}
		System.out.println("SAIU DO GETTIPORETORNO DO COMANDODECLARACAO (VOID)");
		return TipoPrimitivo.VOID;
	}
}
