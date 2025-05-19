package li2.plp.imperative2.memory;

import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.ValorConcreto;
import li2.plp.expressions2.expression.Valor;
import li2.plp.expressions1.util.Tipo;
import li2.plp.imperative2.declaration.TipoSubAlgoritmo;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;

public class ValorFuncao extends ValorConcreto<DefProcedimento> {

    private DefProcedimento funcao;

    public ValorFuncao(DefProcedimento funcao) {
        super(funcao);
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return "função " + funcao.toString();
    }

    public boolean eIgual(ValorConcreto<?> outro) {
        if (outro instanceof ValorFuncao) {
            ValorFuncao outraFuncao = (ValorFuncao) outro;
            return this.funcao.equals(outraFuncao.funcao);
        }
        return false;
    }

    @Override
    public ValorFuncao clone() {
        return new ValorFuncao(this.funcao);
    }

    @Override
    public Expressao reduzir(AmbienteExecucao ambiente) {
        return this;
    }

    @Override
    public boolean checaTipo(li2.plp.expressions2.memory.AmbienteCompilacao ambiente) {
        return true;
    }

    @Override
    public Valor avaliar(AmbienteExecucao ambiente) {
        return this;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente) {
        // Retorna o tipo da função utilizando o ambiente
        return funcao.getTipo();
    }

    public DefProcedimento getValor() {
        return this.funcao;
    }
}
