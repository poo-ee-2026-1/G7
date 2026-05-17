package model;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Núcleo do sistema: gerencia todas as transações do usuário.
 *
 * CONCEITO POO — RESPONSABILIDADE ÚNICA:
 * Esta classe cuida apenas de armazenar, buscar e calcular transações.
 * A geração de relatórios formatados fica nas classes do pacote "relatorio".
 */
public class GerenciadorFinanceiro {

    private final Usuario usuario;
    // CONCEITO POO — POLIMORFISMO:
    // A lista armazena Transacao (tipo pai). Dentro dela cabem
    // TransacaoFixa e TransacaoVariavel — tratadas de forma unificada.
    private final List<Transacao> transacoes = new ArrayList<>();

    public GerenciadorFinanceiro(Usuario usuario) {
        this.usuario = usuario;
    }

    // ── Operações básicas ────────────────────────────────────────────────────

    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
    }

    public boolean removerTransacao(int id) {
        return transacoes.removeIf(t -> t.getId() == id);
    }

    public List<Transacao> listarTodas() {
        return new ArrayList<>(transacoes);
    }

    // ── Filtros ──────────────────────────────────────────────────────────────

    public List<Transacao> filtrarPorMes(int ano, Month mes) {
        return transacoes.stream()
                .filter(t -> t.getData().getYear() == ano
                          && t.getData().getMonth() == mes)
                .collect(Collectors.toList());
    }

    public List<Transacao> filtrarPorCategoria(Categoria categoria) {
        return transacoes.stream()
                .filter(t -> t.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    // ── Cálculos de saldo ────────────────────────────────────────────────────

    /**
     * POLIMORFISMO em ação: chama getValorLiquido() sem saber se é
     * TransacaoFixa ou TransacaoVariavel — cada uma responde corretamente.
     */
    public double calcularSaldo() {
        return transacoes.stream()
                .mapToDouble(Transacao::getValorLiquido)
                .sum();
    }

    public double calcularSaldoMensal(int ano, Month mes) {
        return filtrarPorMes(ano, mes).stream()
                .mapToDouble(Transacao::getValorLiquido)
                .sum();
    }

    public double calcularTotalReceitas() {
        return transacoes.stream()
                .mapToDouble(t -> Math.max(0, t.getValorLiquido()))
                .sum();
    }

    public double calcularTotalDespesas() {
        return transacoes.stream()
                .mapToDouble(t -> Math.min(0, t.getValorLiquido()))
                .sum();
    }

    public double calcularGastosPorCategoria(Categoria categoria) {
        return filtrarPorCategoria(categoria).stream()
                .mapToDouble(t -> Math.abs(t.getValorLiquido()))
                .sum();
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public Usuario getUsuario()        { return usuario; }
    public int getTotalTransacoes()    { return transacoes.size(); }
}
