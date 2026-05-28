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
        this(contadorId++, descricao, valor, categoria, data, metodoPagamento);
    }

    /**
     * Construtor usado ao carregar transações já existentes no banco de dados.
     * Ele preserva o ID salvo para que a remoção por ID continue funcionando.
     */
    protected Transacao(int id, String descricao, double valor, Categoria categoria, LocalDate data, String metodoPagamento) {
        if (valor <= 0) throw new IllegalArgumentException("O valor deve ser positivo.");
        this.id        = id;
        this.descricao = descricao;
        this.valor     = valor;
        this.categoria = categoria;
        this.data      = data;
        this.metodoPagamento = metodoPagamento;
        atualizarContadorProximo(id);
    }

    public static void atualizarContadorProximo(int idExistente) {
        if (idExistente >= contadorId) {
            contadorId = idExistente + 1;
        }
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
