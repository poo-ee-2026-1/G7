package model;

import java.time.LocalDate;

public class TransacaoVariavel extends Transacao {
    private boolean parcelado;
    private int numeroParcelas;

    public TransacaoVariavel(String descricao, double valor, Categoria categoria,
                             LocalDate data, String metodoPagamento, boolean parcelado, int numeroParcelas) {
        super(descricao, valor, categoria, data, metodoPagamento);
        this.parcelado      = parcelado;
        this.numeroParcelas = parcelado ? numeroParcelas : 1;
    }

    public TransacaoVariavel(int id, String descricao, double valor, Categoria categoria,
                             LocalDate data, String metodoPagamento, boolean parcelado, int numeroParcelas) {
        super(id, descricao, valor, categoria, data, metodoPagamento);
        this.parcelado      = parcelado;
        this.numeroParcelas = parcelado ? numeroParcelas : 1;
    }

    public TransacaoVariavel(String descricao, double valor, Categoria categoria, LocalDate data, String metodoPagamento) {
        this(descricao, valor, categoria, data, metodoPagamento, false, 1);
    }

    @Override
    public double getValorLiquido() {
        return -getValor();
    }

    @Override
    public String getTipo() {
        return parcelado
                ? "Despesa Variável (" + numeroParcelas + "x)"
                : "Despesa Variável";
    }

    public boolean isParcelado()        { return parcelado; }
    public int getNumeroParcelas()      { return numeroParcelas; }
}
