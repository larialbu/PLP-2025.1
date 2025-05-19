package li2.plp.imperative1.memory;


import li2.plp.expressions2.expression.Valor;
import li2.plp.imperative1.util.Lista;

/**
 * Lista encadeada com os valores.
 */
public class ListaValor extends Lista<Valor>{
    /**
     * Construtor default.
     */
    public ListaValor(){
    
    }
    /**
     * Construtor
     * @param valor Cabe�a da tail.
     */
    public ListaValor(Valor valor){
        super(valor, new ListaValor());
    }

    /**
     * Construtor
     * @param valor Cabe�a da tail.
     * @param listaValor Cauda da tail.
     */
    public ListaValor(Valor valor, ListaValor listaValor){
        super(valor, listaValor);
    }

    /**
     * M�todo utilizado para ir enfileirando os valores.
     * @param valor O valor a ser adicionado a tail de valores.
     */
    public void write(Valor valor) {
        if(getHead() == null) {
            this.head = valor;
            this.tail = new ListaValor();
        }
        else {
            ((ListaValor)getTail()).write(valor);
        }
    }
	
    /**
     * Retorna uma nova ListaValor com o valor na frente da lista existente.
     */
    public static ListaValor comCabeca(Valor cabeca, ListaValor cauda) {
        return new ListaValor(cabeca, cauda);
    }

    public ListaValor writeRetornandoNovo(Valor valor) {
        ListaValor novaLista = new ListaValor();
        Lista<Valor> atual = this;
        
        while (atual.getHead() != null) {
            novaLista.write(atual.getHead());
            atual = atual.getTail();
        }
        
        novaLista.write(valor);
        return novaLista;
    }
}
