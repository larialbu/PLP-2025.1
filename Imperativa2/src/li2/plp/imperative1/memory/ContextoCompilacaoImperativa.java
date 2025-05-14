package li2.plp.imperative1.memory;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.Contexto;
import li2.plp.expressions2.memory.ContextoCompilacao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative2.declaration.DefProcedimento;

public class ContextoCompilacaoImperativa extends ContextoCompilacao 
	implements AmbienteCompilacaoImperativa {

    private Contexto<DefProcedimento> contextoProcedimentos;

    /**
     * A pilha de blocos de contexto.
     */    
    private ListaValor entrada;

    /**
     * Construtor da classe.
     */
    public ContextoCompilacaoImperativa(ListaValor entrada){
        super();
        this.entrada = entrada;
        this.contextoProcedimentos = new Contexto<>();     
    }

    public void incrementa(){
        super.incrementa();
        contextoProcedimentos.incrementa();
    }

    public void restaura(){
        super.restaura();
        contextoProcedimentos.restaura();
    }

    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
    		EntradaVaziaException {
        if(entrada == null || entrada.getHead() == null) {
            throw new EntradaVaziaException();
        }
        Tipo aux = entrada.getHead().getTipo(this);
        entrada = (ListaValor)entrada.getTail();
        return aux;            
    }
    
    @Override
    public DefProcedimento getProcedimento(Id id) throws IdentificadorNaoDeclaradoException{
        try{
            System.out.println("ENTROU NO GETPROCEDIMENTO DO AMBIENTECOMPILACAOIMPERATIVA PARA: " + id);
            System.out.println(this.contextoProcedimentos);
            return this.contextoProcedimentos.get(id);
        } catch (VariavelNaoDeclaradaException e) {
            throw new IdentificadorNaoDeclaradoException("Identificador não declarado: " + id.getIdName());
        }
    }

    public void mapProcedimento(Id id, DefProcedimento procedimento) throws IdentificadorJaDeclaradoException{
        System.out.println("ENTROU NO MAPPROCEDIMENTO DO AMBIENTECOMPILACAOIMPERATIVA");
        try{
            System.out.println(id + " " + procedimento);
            this.contextoProcedimentos.map(id, procedimento);
        } catch (VariavelJaDeclaradaException e){
            throw new IdentificadorNaoDeclaradoException("Identificador ja declarado: " + id.getIdName());
        }
    }
}

