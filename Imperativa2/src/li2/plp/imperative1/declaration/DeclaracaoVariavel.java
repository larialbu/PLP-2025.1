package li2.plp.imperative1.declaration;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;

public class DeclaracaoVariavel extends Declaracao {

	private Id id;
	private Expressao expressao;

	public DeclaracaoVariavel(Id id, Expressao expressao) {
		super();
		this.id = id;
		this.expressao = expressao;
	}

	/**
	 * Cria um mapeamento do identificador para o valor da express�o desta
	 * declara��o no AmbienteExecucao
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return o ambiente modificado pela inicializa��o da vari�vel.
	 */
	@Override
	public AmbienteExecucaoImperativa elabora(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException {
		ambiente.map(getId(), getExpressao().avaliar(ambiente));
		return ambiente;
	}

	public Expressao getExpressao() {
		return this.expressao;
	}

	public Id getId() {
		return this.id;
	}

	/**
	 * Verifica se a declara��o est� bem tipada, ou seja, se a express�o de
	 * inicializa��o est� bem tipada, e cria o mapeamento da variavel para o seu
	 * tipo correspondente
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            seus tipos.
	 * 
	 * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
	 *         <code>false</code> caso contrario.
	 * 
	 */
	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException {
		System.out.println("ENTROU NO CHECATIPO DE DECLARACAOVARIAVEL");
		boolean result = getExpressao().checaTipo(ambiente);
		System.out.println("SAIU DO CHECATIPO DE DECLARACAOVARIAVEL: " + result);
		return result;
	}

	@Override
	public void mapa(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException{
		System.out.println("ENTROU NO MAPA DE DECLARACAOVARIAVEL: " + id);
		Id id = getId();
		Tipo tipo = getExpressao().getTipo(ambiente);
		ambiente.map(id, tipo);
		System.out.println("SAIU DO MAPA DE DECLARACAOVARIAVEL");
	}
}
