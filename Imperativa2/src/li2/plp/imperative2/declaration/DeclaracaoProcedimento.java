package li2.plp.imperative2.declaration;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative1.declaration.Declaracao;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;

public class DeclaracaoProcedimento extends Declaracao {

	private Id id;
	private DefProcedimento defProcedimento;

	public DeclaracaoProcedimento(Id id, DefProcedimento defProcedimento) {
		super();
		this.id = id;
		this.defProcedimento = defProcedimento;
	}

	@Override
	public AmbienteExecucaoImperativa elabora(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		((AmbienteExecucaoImperativa2) ambiente).mapProcedimento(getId(),
				getDefProcedimento());
		return ambiente;
	}

	private Id getId() {
		return this.id;
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {

		boolean resposta;

		ambiente.map(id, defProcedimento.getTipo());

		DefProcedimento procedimento = getDefProcedimento();
		ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();

		if (parametrosFormais.checaTipo(ambiente)) {
			ambiente.incrementa();
			ambiente = parametrosFormais.elabora(ambiente);

			Comando comando = procedimento.getComando();
			resposta = comando.checaTipo(ambiente);

			if (comando.contemReturn()) {
				Tipo tipoRetornado = comando.getTipoRetorno(ambiente);

				procedimento.setTipoRetorno(tipoRetornado); // <- agora atualiza

				if (!TipoPrimitivo.VOID.eIgual(tipoRetornado)) {
					// Agora sim: procedimento *retorna valor*
					Tipo tipoDeclarado = procedimento.getTipo();

					if (!tipoDeclarado.eIgual(tipoRetornado)) {
						resposta = false;
					}
				} else {
					// Return do tipo VOID mas não devia retornar nada
					if (!TipoPrimitivo.VOID.eIgual(procedimento.getTipo())) {
						resposta = false;
					}
				}
			} else {
				// Se não tem return, o tipo declarado precisa ser VOID
				if (!TipoPrimitivo.VOID.eIgual(procedimento.getTipo())) {
					resposta = false;
				}
			}

			ambiente.restaura();
			
		} else {
			resposta = false;
		}

		return resposta;
	}

	private DefProcedimento getDefProcedimento() {
		return this.defProcedimento;
	}
}
