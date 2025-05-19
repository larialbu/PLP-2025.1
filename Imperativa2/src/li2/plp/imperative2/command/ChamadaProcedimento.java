package li2.plp.imperative2.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.imperative2.util.TipoProcedimento;
import li2.plp.imperative2.declaration.TipoSubAlgoritmo;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.expressions2.expression.Valor;
import li2.plp.imperative2.memory.ValorFuncao;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;
import li2.plp.imperative2.util.TipoProcedimento;
import li2.plp.expressions2.expression.Expressao;
import java.util.List;
import java.util.ArrayList;

public class ChamadaProcedimento implements Comando {

	private Id nomeProcedimento;

	private ListaExpressao parametrosReais;

	private List<Id> decorators;

	public ChamadaProcedimento(Id nomeProcedimento,
			ListaExpressao parametrosReais, List<Id> decorators) {
		this.nomeProcedimento = nomeProcedimento;
		this.parametrosReais = parametrosReais;
		this.decorators = decorators;
		System.out.println("CONSTRUTOR DE CHAMADAPROCEDIMENTO");
	}

	public AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa amb)
			throws IdentificadorNaoDeclaradoException,
			IdentificadorJaDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {

		AmbienteExecucaoImperativa2 ambiente = (AmbienteExecucaoImperativa2) amb;
		DefProcedimento procedimento = ambiente.getProcedimento(nomeProcedimento);
		System.out.println("ENTROU NO EXECUTAR DE CHAMADAPROCEDIMENTO");

		// Incrementa o ambiente para preparar a execução do procedimento
		ambiente.incrementa();
		ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();
		AmbienteExecucaoImperativa2 aux = bindParameters(ambiente, parametrosFormais);

		System.out.println("DECORATORS: " + decorators);

		// Processa os decorators, do último para o primeiro
		for (int i = decorators.size() - 1; i >= 0; i--) {
			Id decoratorId = decorators.get(i);
			System.out.println("GETPROCEDIMENTO: " + decoratorId);
			DefProcedimento decoratorProc = aux.getProcedimento(decoratorId);

			// Incrementa o ambiente para o decorator
			aux.incrementa();

			// Prepara os parâmetros do decorador
			ListaDeclaracaoParametro parametrosDecorator = decoratorProc.getParametrosFormais();

			// Cria uma lista de valores para passar para o decorador
			ListaValor listaValorDecorador = new ListaValor();
			
			// Adiciona a função original como primeiro parâmetro do decorator
			listaValorDecorador = new ListaValor(new ValorFuncao(procedimento), listaValorDecorador);

			// Obtém os argumentos originais da função e os adiciona na lista de valores
			listaValorDecorador = parametrosReais.avaliar(aux);

			// Mapeia os parâmetros no ambiente do decorador
			aux.incrementa();
			AmbienteExecucaoImperativa2 ambienteDecorador = bindParameters(aux, parametrosDecorator);

			System.out.println("EXECUTANDO DECORADOR: " + decoratorId);
			ambienteDecorador = (AmbienteExecucaoImperativa2) decoratorProc.getComando().executar(ambienteDecorador);

			// Restaura o ambiente após o decorator
			aux.restaura();
		}

		// Executa o procedimento final (função original)
		aux = (AmbienteExecucaoImperativa2) procedimento.getComando().executar(aux);
		System.out.println("SAIU DO EXECUTAR DE CHAMADAPROCEDIMENTO");
		aux.restaura();
		return aux;
	}

	/**
	 * insere no contexto o resultado da associacao entre cada parametro formal
	 * e seu correspondente parametro atual
	 */
	private AmbienteExecucaoImperativa2 bindParameters(
			AmbienteExecucaoImperativa2 ambiente,
			ListaDeclaracaoParametro parametrosFormais)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		ListaValor listaValor = parametrosReais.avaliar(ambiente);
		System.out.println("ENTROU NO BINDPARAMETERS DE CHAMADAPROCEDIMENTO");
		while (listaValor.length() > 0) {
			System.out.println("ID: " + parametrosFormais.getHead().getId() + " VALOR: " + listaValor.getHead());
			ambiente.map(parametrosFormais.getHead().getId(), listaValor
					.getHead());
			parametrosFormais = (ListaDeclaracaoParametro) parametrosFormais
					.getTail();
			listaValor = (ListaValor) listaValor.getTail();
		}
		System.out.println("SAIU DO BINDPARAMETERS DE CHAMADAPROCEDIMENTO");
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos desta chamada de procedimento, onde os
	 * tipos dos parametros formais devem ser iguais aos tipos dos parametros
	 * reais na ordem em que se apresentam.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            tipos.
	 * @return <code>true</code> se a chamada de procedimeno est� bem tipada;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa amb)
			throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException {
		
		System.out.println("ENTROU NO CHECATIPO DE CHAMADAPROCEDIMENTO: " + this.nomeProcedimento);

		Tipo tipoDeclarado = amb.get(this.nomeProcedimento);
		System.out.println("TIPO DECLARADO: " + tipoDeclarado.getClass());

		List<Tipo> parametrosReaisTipos = parametrosReais.getTipos(amb);

		TipoProcedimento tipoEsperado;

		if (tipoDeclarado instanceof TipoSubAlgoritmo) {
			TipoSubAlgoritmo tipoSub = (TipoSubAlgoritmo) tipoDeclarado;
			tipoEsperado = new TipoProcedimento(tipoSub.getParametros().getTipos(), tipoSub.getTipoRetorno());
		} else if (tipoDeclarado instanceof TipoProcedimento) {
			tipoEsperado = (TipoProcedimento) tipoDeclarado;
		} else {
			System.out.println("TIPO NAO É PROCEDIMENTO OU FUNÇÃO");
			return false;
		}

		TipoProcedimento tipoInformado = new TipoProcedimento(parametrosReaisTipos, tipoEsperado.getTipoRetorno());

		System.out.println("TIPO ESPERADO: " + tipoEsperado);
		System.out.println("TIPO INFORMADO: " + tipoInformado);

		return tipoEsperado.eIgual(tipoInformado);
	}

	public List<Id> getDecorators(){
		System.out.println("GETDECORATORS DE CHAMADAPROCEDIMENTO");
		return decorators;
	}

	public boolean contemReturn(AmbienteExecucaoImperativa ambiente) {
		System.out.println("ENTROU NO CONTEMRETURN AMB DE CHAMADAPROCEDIMENTO");
		try {
			AmbienteExecucaoImperativa2 ambiente2 = (AmbienteExecucaoImperativa2) ambiente;
			DefProcedimento procedimento = ambiente2.getProcedimento(nomeProcedimento);

			System.out.println("SAIU DO CONTEMRETURN DE CHAMADAPROCEDIMENTO");
			return procedimento.getComando().contemReturn();
		} catch (Exception e) {
			System.out.println("SAIU DO CONTEMRETURN DE CHAMADAPROCEDIMENTO");
			return false;
		}
	}

	@Override
	public Tipo getTipoRetorno(AmbienteCompilacaoImperativa ambiente){
		try{
			System.out.println("ENTROU NO GETTIPORETORNO CHAMADAPROCEDIMENTO");
			DefProcedimento procedimento = ambiente.getProcedimento(nomeProcedimento);

 			if (procedimento.getComando().contemReturn()) {
				System.out.println("SAIU DO GETTIPORETORNO DE CHAMADAPROCEDIMENTO");
            	return procedimento.getComando().getTipoRetorno(ambiente);
        	}
    	} catch (Exception e) {
			System.out.println("SAIU DO GETTIPORETORNO (EXCEPTION) DE CHAMADAPROCEDIMENTO");
    		return null;
    	}
    	return TipoPrimitivo.VOID;
	}

	@Override
	public boolean contemReturn() {
		System.out.println("ENTROU NO CONTEMRETURN DE CHAMADAPROCEDIMENTO");
		return false;
	}
}