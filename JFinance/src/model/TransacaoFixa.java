package model;

import java.time.LocalDate;

public class TransacaoFixa extends Transacao {
    public enum NaturezaFixa { RECEITA, DESPESA }
    private final NaturezaFixa natureza;

    public TransacaoFixa(String descricao, double valor, Categoria categoria,
                         LocalDate data, String metodoPagamento, NaturezaFixa natureza) {
        super(descricao, valor, categoria, data, metodoPagamento);
        this.natureza = natureza;
    }

    public TransacaoFixa(int id, String descricao, double valor, Categoria categoria,
                         LocalDate data, String metodoPagamento, NaturezaFixa natureza) {
        super(id, descricao, valor, categoria, data, metodoPagamento);
        this.natureza = natureza;
    }

    @Override
    public double getValorLiquido() {
        return natureza == NaturezaFixa.RECEITA ? getValor() : -getValor();
    }

    @Override
    public String getTipo() {
        return natureza == NaturezaFixa.RECEITA ? "Receita Fixa" : "Despesa Fixa";
    }

    public NaturezaFixa getNatureza() { return natureza; }
}
