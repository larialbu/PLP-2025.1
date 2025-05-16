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

	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {

		boolean resposta;
		System.out.println("ENTROU NO CHECATIPO DE DECLARACAOPROCEDIMENTO para: " + id + " " + defProcedimento.getTipo());

		ambiente.map(id, defProcedimento.getTipo());
		ambiente.mapProcedimento(id, defProcedimento);


		DefProcedimento procedimento = getDefProcedimento();
		ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();

		if (parametrosFormais.checaTipo(ambiente)) {
			System.out.println("PRIMEIRO IF DE CHECATIPO");

			ambiente.incrementa();
			ambiente = parametrosFormais.elabora(ambiente);

			Comando comando = procedimento.getComando();
			resposta = comando.checaTipo(ambiente);

			if (comando.contemReturn()) {
				System.out.println("SEGUNDO IF DE CHECATIPO");
				Tipo tipoRetornado = comando.getTipoRetorno(ambiente);

				// Verificação específica para tipos função
				if (tipoRetornado instanceof TipoSubAlgoritmo) {
					TipoSubAlgoritmo tipoRetornadoFunc = (TipoSubAlgoritmo) tipoRetornado;
					Tipo tipoDeclarado = procedimento.getTipoRetorno(ambiente);

					if (tipoDeclarado instanceof TipoSubAlgoritmo) {
						TipoSubAlgoritmo tipoDeclaradoFunc = (TipoSubAlgoritmo) tipoDeclarado;

						if (!tipoRetornadoFunc.eIgual(tipoDeclaradoFunc)) {
							System.out.println("TIPO FUNÇÃO NÃO COMPATÍVEL");
							resposta = false;
						}
					} else {
						System.out.println("ERRO: Tipo declarado não é função.");
						resposta = false;
					}
				} else if (!TipoPrimitivo.VOID.eIgual(tipoRetornado)) {
					System.out.println("TERCEIRO IF DE CHECATIPO");
					Tipo tipoDeclarado = procedimento.getTipoRetorno(ambiente);
					System.out.println("Declarado: " + tipoDeclarado + ". Retornado: " + tipoRetornado);
					if (!tipoDeclarado.eIgual(tipoRetornado)) {
						System.out.println("QUARTO IF DE CHECATIPO");
						resposta = false;
					}
				} else {
					System.out.println("RETURN VOID MAS NAO DEVIA RETORNAR NADA");
					// Return do tipo VOID mas não devia retornar nada
					if (!TipoPrimitivo.VOID.eIgual(procedimento.getTipoRetorno(ambiente))) {
						System.out.println("QUINTO IF DE CHECATIPO");
						resposta = false;
					}
				}
			} else {
				System.out.println("SEGUNDO ELSE DE CHECATIPO");
				// Se não tem return, o tipo declarado precisa ser VOID
				Tipo tipoDeclarado = procedimento.getTipoRetorno(ambiente);
				System.out.println("Declarado: " + tipoDeclarado);
				if (!TipoPrimitivo.VOID.eIgual(tipoDeclarado)) {
					System.out.println("SEXTO IF DE CHECATIPO");
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
		System.out.println("ENTROU NO GETDEFPROCEDIMENTO DE DECLARACAOPROCEDIMENTO");
		return this.defProcedimento;
	}
}
