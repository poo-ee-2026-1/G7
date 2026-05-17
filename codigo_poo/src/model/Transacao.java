package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe abstrata base para todas as transações.
 */
public abstract class Transacao {

    private static int contadorId = 1;

    private final int id;
    private String descricao;
    private double valor;
    private Categoria categoria;
    private LocalDate data;
    private String metodoPagamento;

    public Transacao(String descricao, double valor, Categoria categoria, LocalDate data, String metodoPagamento) {
        if (valor <= 0) throw new IllegalArgumentException("O valor deve ser positivo.");
        this.id        = contadorId++;
        this.descricao = descricao;
        this.valor     = valor;
        this.categoria = categoria;
        this.data      = data;
        this.metodoPagamento = metodoPagamento;
    }

    public abstract double getValorLiquido();
    public abstract String getTipo();

    // ── Getters ──────────────────────────────────────────────────────────────
    public int getId()             { return id; }
    public String getDescricao()   { return descricao; }
    public double getValor()       { return valor; }
    public Categoria getCategoria(){ return categoria; }
    public LocalDate getData()     { return data; }
    public String getMetodoPagamento() { return metodoPagamento; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("O valor deve ser positivo.");
        this.valor = valor;
    }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setData(LocalDate data)           { this.data = data; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("[#%d] %-18s | %-22s | %-12s | R$ %8.2f | %s | %s",
                id, getTipo(), descricao, categoria, getValorLiquido(), data.format(fmt), metodoPagamento);
    }
}
