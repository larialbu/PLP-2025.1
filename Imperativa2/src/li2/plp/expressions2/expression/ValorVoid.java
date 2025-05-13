package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.expression.Valor;
import li2.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Objetos desta classe encapsulam valor inteiro.
 */
public class ValorVoid extends ValorConcreto<Void> {
    public ValorVoid() {
        super(null); // Passa null explicitamente como valor "void"
    }
	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.VOID;
	}

	public ValorVoid clone(){
		return new ValorVoid();
	}
}
