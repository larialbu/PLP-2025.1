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
		System.out.println("ENTROU NO CONSTRUTOR DE DECLARACAOPROCEDIMENTO");
	}

	@Override
	public AmbienteExecucaoImperativa elabora(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		System.out.println("ENTROU NO ELABORA DE DECLARACAOPROCEDIMENTO");
		((AmbienteExecucaoImperativa2) ambiente).mapProcedimento(getId(),
				getDefProcedimento());
		return ambiente;
	}

	private Id getId() {
		System.out.println("ENTROU NO GETID DE DECLARACAOPROCEDIMENTO");
		return this.id;
	}

	public void mapa(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException {
		System.out.println("ENTROU NO MAPA DE DECLARACAOPROCEDIMENTO");
		ambiente.map(id, defProcedimento.getTipo());
		ambiente.mapProcedimento(id, defProcedimento);
		System.out.println("PROCEDIMENTO " + id + " MAPEADO");
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
				IdentificadorNaoDeclaradoException, EntradaVaziaException {

		boolean resposta;

		System.out.println("ENTROU NO CHECATIPO DE DECLARACAOPROCEDIMENTO: " + id);

		DefProcedimento procedimento = getDefProcedimento();
		ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();

		if (parametrosFormais.checaTipo(ambiente)) {
			ambiente.incrementa();
			ambiente = parametrosFormais.elabora(ambiente);

			Comando comando = procedimento.getComando();
			resposta = comando.checaTipo(ambiente);

			if (comando.contemReturn()) {
				Tipo tipoRetornado = comando.getTipoRetorno(ambiente);
				Tipo tipoDeclarado = procedimento.getTipoRetorno(ambiente);

				if (tipoRetornado instanceof TipoSubAlgoritmo) {
					if (!(tipoDeclarado instanceof TipoSubAlgoritmo) ||
						!tipoRetornado.eIgual(tipoDeclarado)) {
						resposta = false;
					}
				} else if (!TipoPrimitivo.VOID.eIgual(tipoRetornado)) {
					if (!tipoDeclarado.eIgual(tipoRetornado)) {
						resposta = false;
					}
				} else if (!TipoPrimitivo.VOID.eIgual(tipoDeclarado)) {
					resposta = false;
				}
			} else {
				// Nenhum return: o tipo declarado deve ser void
				if (!TipoPrimitivo.VOID.eIgual(procedimento.getTipoRetorno(ambiente))) {
					resposta = false;
				}
			}

			ambiente.restaura();
		} else {
			resposta = false;
		}
		System.out.println("SAIU DO CHECATIPO DE DECLARACAOPROCEDIMENTO");
		return resposta;
	}

	private DefProcedimento getDefProcedimento() {
		System.out.println("GETDEFPROCEDIMENTO DE DECLARACAOPROCEDIMENTO");
		return this.defProcedimento;
	}
}
