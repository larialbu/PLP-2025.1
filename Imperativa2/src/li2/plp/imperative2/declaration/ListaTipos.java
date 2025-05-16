package li2.plp.imperative2.declaration;

import java.util.ArrayList;
import java.util.List;
import li2.plp.expressions1.util.Tipo;

public class ListaTipos {

    private List<Tipo> tipos;
    
    public ListaTipos(){
        tipos = new ArrayList<>();
    }

    public void adicionar(Tipo tipo){
        tipos.add(tipo);
    }

    public List<Tipo> getTipos(){
        return tipos;
    }

    public boolean eIgual(ListaTipos outro) {
        if (this.tipos.size() != outro.tipos.size()) {
            return false;
        }
        for (int i = 0; i < this.tipos.size(); i++) {
            if (!this.tipos.get(i).eIgual(outro.tipos.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        return tipos.toString();
    }
}