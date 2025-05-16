package li2.plp.imperative1.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.ValorBooleano;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.imperative2.memory.TiposRetornoIncompativeisException;

public class IfThenElse implements Comando {

	private Expressao expressao;

	private Comando comandoThen;

	private Comando comandoElse;

	public IfThenElse(Expressao expressao, Comando comandoThen,
			Comando comandoElse) {
		this.expressao = expressao;
		this.comandoThen = comandoThen;
		this.comandoElse = comandoElse;
		System.out.println("CONSTRUTOR DE IFTHENELSE");
	}

	/**
	 * Implementa o comando <code>if then else</code>.
	 * 
	 * @param ambiente
	 *            o ambiente de execu��o.
	 * 
	 * @return o ambiente depois de modificado pela execu��o do comando
	 *         <code>if then else</code>.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		if (((ValorBooleano) expressao.avaliar(ambiente)).valor())
			return comandoThen.executar(ambiente);
		else
			return comandoElse.executar(ambiente);
	}

	/**
	 * Realiza a verificacao de tipos da express�o e dos comandos do comando
	 * <code>if then else</code>
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se a express�o e os comando s�o bem tipados;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		return expressao.checaTipo(ambiente)
				&& expressao.getTipo(ambiente).eBooleano()
				&& comandoThen.checaTipo(ambiente)
				&& comandoElse.checaTipo(ambiente);
	}

	@Override
	public boolean contemReturn(){
		boolean cr = comandoThen.contemReturn() && comandoElse.contemReturn();
		System.out.println("IFTHENELSE: CONTEMRETURN: " + cr);
		return cr;
	}

	@Override
	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa amb){
		System.out.println("ENTROU EM GETTIPORETORNO DE IFTHENELSE");
		if(this.contemReturn()){



			Tipo tipoThen = comandoThen.getTipoRetorno(amb);
			Tipo tipoElse = comandoElse.getTipoRetorno(amb);

			boolean thenTemReturn = !(tipoThen instanceof TipoPrimitivo) || !((TipoPrimitivo) tipoThen).equals(TipoPrimitivo.VOID);
			boolean elseTemReturn = !(tipoElse instanceof TipoPrimitivo) || !((TipoPrimitivo) tipoElse).equals(TipoPrimitivo.VOID);

			if (thenTemReturn && elseTemReturn) {
				if (tipoThen.eIgual(tipoElse)) {
					System.out.println("Todos os tipos de retorno compativeis");
					return tipoThen;
				} else {
					throw new TiposRetornoIncompativeisException(
						"Tipos de retorno diferentes: " + tipoThen + " e " + tipoElse
					);
				}
			} else if (thenTemReturn ^ elseTemReturn) {
				throw new TiposRetornoIncompativeisException(
					"Apenas um dos ramos do if contém comando de retorno"
				);
			} else {
				System.out.println("Todos retornam void");
				return TipoPrimitivo.VOID;  // Se nenhum dos ramos retornar um tipo
			}
		}
		return TipoPrimitivo.VOID;  // Se não houver nenhum `return` nos ramos
		}
}
