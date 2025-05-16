package li2.plp.imperative2.memory;

public class ErroTipoRetornoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErroTipoRetornoException(String msg) {
		super(msg);
	}

	public ErroTipoRetornoException() {
		super("Erro ao obter tipo de Retorno");
	}
}