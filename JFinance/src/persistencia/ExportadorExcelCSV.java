package persistencia;

import model.Transacao;
import model.TransacaoFixa;
import model.TransacaoVariavel;
import model.Usuario;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Exporta os dados financeiros para CSV separado por ponto e vírgula.
 *
 * O Excel abre esse formato diretamente e, em ambientes em português,
 * o ponto e vírgula costuma evitar conflitos com a vírgula decimal.
 */
public class ExportadorExcelCSV {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void exportar(Path arquivoDestino, Usuario usuario, List<Transacao> transacoes) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivoDestino, StandardCharsets.UTF_8)) {
            writer.write("sep=;\n");
            writer.write("Email;Nome;ID;Data;Tipo;Descrição;Categoria;Método de pagamento;Valor original;Valor líquido;Parcelado;Número de parcelas\n");

            for (Transacao t : transacoes) {
                writer.write(linha(usuario, t));
                writer.newLine();
            }
        }
    }

    private String linha(Usuario usuario, Transacao transacao) {
        String parcelado = "Não";
        int numeroParcelas = 1;

        // CORRIGIDO: Pattern Matching (instanceof com variável) substituído
        // por instanceof tradicional + cast explícito (compatível com Java 8+)
        if (transacao instanceof TransacaoVariavel) {
            TransacaoVariavel variavel = (TransacaoVariavel) transacao;
            parcelado = variavel.isParcelado() ? "Sim" : "Não";
            numeroParcelas = variavel.getNumeroParcelas();
        }

        if (transacao instanceof TransacaoFixa) {
            parcelado = "Não";
            numeroParcelas = 1;
        }

        return String.join(";",
                escapar(usuario.getEmail()),
                escapar(usuario.getNome()),
                String.valueOf(transacao.getId()),
                escapar(transacao.getData().format(FORMATO_DATA)),
                escapar(transacao.getTipo()),
                escapar(transacao.getDescricao()),
                escapar(transacao.getCategoria().toString()),
                escapar(transacao.getMetodoPagamento()),
                formatarNumero(transacao.getValor()),
                formatarNumero(transacao.getValorLiquido()),
                escapar(parcelado),
                String.valueOf(numeroParcelas)
        );
    }

    private String formatarNumero(double valor) {
        return String.format(Locale.US, "%.2f", valor).replace('.', ',');
    }

    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        String valor = texto.replace("\"", "\"\"");
        if (valor.contains(";") || valor.contains("\n") || valor.contains("\r") || valor.contains("\"")) {
            return "\"" + valor + "\"";
        }
        return valor;
    }
}
