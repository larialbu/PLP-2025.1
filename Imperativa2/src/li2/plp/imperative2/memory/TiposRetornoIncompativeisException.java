package li2.plp.imperative2.memory;

public class TiposRetornoIncompativeisException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TiposRetornoIncompativeisException(String msg) {
		super(msg);
	}

	public TiposRetornoIncompativeisException() {
		super("Tipos de retorno diferentes em comandos sequenciais.");
	}
}