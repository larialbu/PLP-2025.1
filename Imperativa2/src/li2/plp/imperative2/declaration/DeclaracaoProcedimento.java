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

		Tipo tipo = defProcedimento.getTipo();
		ambiente.mapProcedimento(id, defProcedimento);
		System.out.println("PROCEDIMENTO " + id + " MAPPROCEDIMENTO");

		if(defProcedimento.retornaValor()){
			ListaTipos listaTipos = new ListaTipos();
			ListaDeclaracaoParametro lista = defProcedimento.getParametrosFormais();

			while (lista != null && lista.getHead() != null){
				listaTipos.adicionar(lista.getHead().getTipo());
				lista = (ListaDeclaracaoParametro) lista.getTail();
			}

			TipoSubAlgoritmo tipoFunc = new TipoSubAlgoritmo(listaTipos, defProcedimento.getTipoRetorno(ambiente));
			ambiente.map(id, tipoFunc);
			System.out.println("FUNCAO " + id + " MAPEADA COMO: " + tipoFunc);
		} else {
			ambiente.map(id, tipo);
			System.out.println("PROCEDIMENTO: " + id + " MAPEADO COMO: " + tipo);
		}

		System.out.println("SAIU DO MAPA DE DECLARACAOPROCEDIMENTO");
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
			System.out.println("IF: PARAMETROS BEM TIPADOS");
			ambiente.incrementa();
			ambiente = parametrosFormais.elabora(ambiente);

			Comando comando = procedimento.getComando();
			resposta = comando.checaTipo(ambiente);

			if (comando.contemReturn()) {
				System.out.println("IF: COMANDO CONTEM RETURN");
				Tipo tipoRetornado = comando.getTipoRetorno(ambiente);
				Tipo tipoDeclarado = procedimento.getTipoRetorno(ambiente);

				if (tipoRetornado instanceof TipoSubAlgoritmo) {
					System.out.println("IF: TIPORETORNADO EH FUNCAO");
					if (!(tipoDeclarado instanceof TipoSubAlgoritmo) ||
							!tipoRetornado.eIgual(tipoDeclarado)) {
						System.out.println("IF: TIPODECLARADO NAO EH FUNCAO OU TIPOS NAO IGUAIS");
						resposta = false;
					}
				} else if (!TipoPrimitivo.VOID.eIgual(tipoRetornado)) {
					System.out.println("IF: TIPORETORNADO NAO EH VOID");
					if (!tipoDeclarado.eIgual(tipoRetornado)) {
						System.out.println("IF: TIPORETORNADO DIFERENTE DE TIPODECLARADO");
						resposta = false;
					}
				} else if (!TipoPrimitivo.VOID.eIgual(tipoDeclarado)) {
					System.out.println("IF: TIPO RETORNADO EH VOID E DECLARADO NAO");
					resposta = false;
				}
			} else {
				System.out.println("IF: COMANDO NAO CONTEM RETURN");
				if (!TipoPrimitivo.VOID.eIgual(procedimento.getTipoRetorno(ambiente))) {
					System.out.println("IF: TIPODECLARADO NAO EH VOID");
					resposta = false;
				}
			}

			ambiente.restaura();
		} else {
			System.out.println("IF: PARAMETROS MAL TIPADOS");
			resposta = false;
		}
		System.out.println("SAIU DO CHECATIPO DE DECLARACAOPROCEDIMENTO: " + resposta);
		return resposta;
	}

	private DefProcedimento getDefProcedimento() {
		System.out.println("GETDEFPROCEDIMENTO DE DECLARACAOPROCEDIMENTO");
		return this.defProcedimento;
	}
}
