package model;

import persistencia.BancoDadosFinanceiro;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Núcleo do sistema: gerencia todas as transações do usuário.
 *
 * CONCEITO POO — RESPONSABILIDADE ÚNICA:
 * Esta classe cuida de armazenar, buscar e calcular transações em memória,
 * delegando a persistência para a classe BancoDadosFinanceiro.
 */
public class GerenciadorFinanceiro {

    private final Usuario usuario;
    private final BancoDadosFinanceiro bancoDados;

    // CONCEITO POO — POLIMORFISMO:
    // A lista armazena Transacao (tipo pai). Dentro dela cabem
    // TransacaoFixa e TransacaoVariavel — tratadas de forma unificada.
    private final List<Transacao> transacoes = new ArrayList<>();

    public GerenciadorFinanceiro(Usuario usuario) {
        this.usuario = usuario;
        this.bancoDados = new BancoDadosFinanceiro();
        this.bancoDados.salvarOuAtualizarUsuario(usuario);
        this.transacoes.addAll(bancoDados.carregarTransacoes(usuario.getEmail()));
    }

    // ── Operações básicas ────────────────────────────────────────────────────

    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
        bancoDados.salvarTransacao(usuario.getEmail(), t);
    }

    public boolean removerTransacao(int id) {
        boolean removidoDaMemoria = transacoes.removeIf(t -> t.getId() == id);
        if (removidoDaMemoria) {
            bancoDados.removerTransacao(usuario.getEmail(), id);
        }
        return removidoDaMemoria;
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
