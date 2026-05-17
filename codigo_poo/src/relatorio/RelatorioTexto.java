package relatorio;

import model.*;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * Gera relatórios formatados no console (texto puro).
 *
 * CONCEITO POO — SEPARAÇÃO DE RESPONSABILIDADES:
 * A lógica de negócio fica em GerenciadorFinanceiro; a formatação fica aqui.
 */
public class RelatorioTexto {

    private static final String LINHA  = "--------------------------------------------------------------------------------";
    private static final String LINHA2 = "================================================================================";

    private final GerenciadorFinanceiro gerenciador;

    public RelatorioTexto(GerenciadorFinanceiro gerenciador) {
        this.gerenciador = gerenciador;
    }

    // ── Relatório geral ──────────────────────────────────────────────────────

    public void imprimirResumoGeral() {
        System.out.println("\n" + LINHA2);
        System.out.println("  RESUMO GERAL — " + gerenciador.getUsuario().getNome());
        System.out.println(LINHA2);
        System.out.printf("  Total de transações : %d%n",
                gerenciador.getTotalTransacoes());
        System.out.printf("  Total de receitas   : R$ %,.2f%n",
                gerenciador.calcularTotalReceitas());
        System.out.printf("  Total de despesas   : R$ %,.2f%n",
                Math.abs(gerenciador.calcularTotalDespesas()));
        System.out.printf("  SALDO FINAL         : R$ %,.2f%n",
                gerenciador.calcularSaldo());
        System.out.println(LINHA2 + "\n");
    }

    // ── Relatório mensal ─────────────────────────────────────────────────────

    public void imprimirRelatorioMensal(int ano, Month mes) {
        String nomeMes = mes.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        List<Transacao> lista = gerenciador.filtrarPorMes(ano, mes);

        System.out.println("\n" + LINHA2);
        System.out.printf("  RELATÓRIO MENSAL — %s/%d%n",
                nomeMes.toUpperCase(), ano);
        System.out.println(LINHA2);

        if (lista.isEmpty()) {
            System.out.println("  Nenhuma transação encontrada neste mês.\n");
            return;
        }

        System.out.println(LINHA);
        System.out.printf("  %-6s %-18s %-22s %-12s %-10s %-10s%n",
                "ID", "TIPO", "DESCRIÇÃO", "CATEGORIA", "VALOR", "DATA");
        System.out.println(LINHA);

        for (Transacao t : lista) {
            System.out.println("  " + t);
        }

        System.out.println(LINHA);
        double saldo = gerenciador.calcularSaldoMensal(ano, mes);
        System.out.printf("  SALDO DO MÊS: R$ %,.2f%n", saldo);
        System.out.println(LINHA2 + "\n");
    }

    // ── Relatório por categoria ───────────────────────────────────────────────

    public void imprimirRelatorioPorCategorias() {
        System.out.println("\n" + LINHA2);
        System.out.println("  GASTOS POR CATEGORIA");
        System.out.println(LINHA2);
        System.out.printf("  %-20s %s%n", "CATEGORIA", "TOTAL GASTO");
        System.out.println(LINHA);

        for (Categoria cat : Categoria.values()) {
            double total = gerenciador.calcularGastosPorCategoria(cat);
            if (total > 0) {
                System.out.printf("  %-20s R$ %,.2f%n", cat, total);
            }
        }

        System.out.println(LINHA2 + "\n");
    }

    // ── Lista completa ────────────────────────────────────────────────────────

    public void imprimirTodasTransacoes() {
        List<Transacao> lista = gerenciador.listarTodas();

        System.out.println("\n" + LINHA2);
        System.out.println("  TODAS AS TRANSAÇÕES");
        System.out.println(LINHA2);

        if (lista.isEmpty()) {
            System.out.println("  Nenhuma transação cadastrada.\n");
            return;
        }

        for (Transacao t : lista) {
            System.out.println("  " + t);
        }
        System.out.println(LINHA2 + "\n");
    }
}
