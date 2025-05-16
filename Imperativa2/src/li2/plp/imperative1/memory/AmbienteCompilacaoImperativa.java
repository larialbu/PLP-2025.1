package li2.plp.imperative1.memory;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.*;
import li2.plp.imperative2.declaration.DefProcedimento;

public interface AmbienteCompilacaoImperativa extends AmbienteCompilacao {

	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
		EntradaVaziaException;

    public DefProcedimento getProcedimento(Id id) throws IdentificadorNaoDeclaradoException;

	public void mapProcedimento(Id id, DefProcedimento procedimento) throws IdentificadorJaDeclaradoException;
}
