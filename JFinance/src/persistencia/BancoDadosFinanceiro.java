package persistencia;

import model.Categoria;
import model.Transacao;
import model.TransacaoFixa;
import model.TransacaoVariavel;
import model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de persistência do sistema financeiro.
 *
 * Esta classe centraliza o acesso ao SQLite. O e-mail do usuário é usado como
 * chave de associação para que cada pessoa visualize apenas os próprios dados.
 */
public class BancoDadosFinanceiro {

    private static final String URL = "jdbc:sqlite:controle_financeiro.db";

    public BancoDadosFinanceiro() {
        try {
            // Garante que o driver seja carregado, prevenindo o erro "No suitable driver found"
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Aviso: Driver SQLite não encontrado no classpath. Verifique a pasta lib.");
        }
        inicializarBanco();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void inicializarBanco() {
        // CORRIGIDO: Text Blocks substituídos por String normal (compatível com Java 8+)
        String criarUsuarios =
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "email TEXT PRIMARY KEY, " +
            "nome TEXT NOT NULL" +
            ");";

        String criarTransacoes =
            "CREATE TABLE IF NOT EXISTS transacoes (" +
            "id INTEGER NOT NULL, " +
            "email_usuario TEXT NOT NULL, " +
            "descricao TEXT NOT NULL, " +
            "valor REAL NOT NULL, " +
            "categoria TEXT NOT NULL, " +
            "data TEXT NOT NULL, " +
            "metodo_pagamento TEXT, " +
            "tipo TEXT NOT NULL, " +
            "natureza TEXT, " +
            "parcelado INTEGER NOT NULL DEFAULT 0, " +
            "numero_parcelas INTEGER NOT NULL DEFAULT 1, " +
            "PRIMARY KEY (email_usuario, id), " +
            "FOREIGN KEY (email_usuario) REFERENCES usuarios(email)" +
            ");";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(criarUsuarios);
            stmt.execute(criarTransacoes);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar banco de dados: " + e.getMessage(), e);
        }
    }

    public void salvarOuAtualizarUsuario(Usuario usuario) {
        // CORRIGIDO: Text Block substituído por String normal
        String sql =
            "INSERT INTO usuarios (email, nome) " +
            "VALUES (?, ?) " +
            "ON CONFLICT(email) DO UPDATE SET nome = excluded.nome;";

        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarEmail(usuario.getEmail()));
            ps.setString(2, usuario.getNome());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }

    public List<Transacao> carregarTransacoes(String emailUsuario) {
        List<Transacao> transacoes = new ArrayList<>();
        // CORRIGIDO: Text Block substituído por String normal
        String sql =
            "SELECT id, descricao, valor, categoria, data, metodo_pagamento, " +
            "tipo, natureza, parcelado, numero_parcelas " +
            "FROM transacoes " +
            "WHERE email_usuario = ? " +
            "ORDER BY data, id;";

        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarEmail(emailUsuario));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transacoes.add(criarTransacaoAPartirDoResultado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar transações: " + e.getMessage(), e);
        }

        return transacoes;
    }

    public void salvarTransacao(String emailUsuario, Transacao transacao) {
        // CORRIGIDO: Text Block substituído por String normal
        String sql =
            "INSERT OR REPLACE INTO transacoes " +
            "(id, email_usuario, descricao, valor, categoria, data, metodo_pagamento, " +
            "tipo, natureza, parcelado, numero_parcelas) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transacao.getId());
            ps.setString(2, normalizarEmail(emailUsuario));
            ps.setString(3, transacao.getDescricao());
            ps.setDouble(4, transacao.getValor());
            ps.setString(5, transacao.getCategoria().name());
            ps.setString(6, transacao.getData().toString());
            ps.setString(7, transacao.getMetodoPagamento());

            // CORRIGIDO: Pattern Matching (instanceof com variável) substituído
            // por instanceof tradicional + cast explícito (compatível com Java 8+)
            if (transacao instanceof TransacaoFixa) {
                TransacaoFixa fixa = (TransacaoFixa) transacao;
                ps.setString(8, "FIXA");
                ps.setString(9, fixa.getNatureza().name());
                ps.setInt(10, 0);
                ps.setInt(11, 1);
            } else if (transacao instanceof TransacaoVariavel) {
                TransacaoVariavel variavel = (TransacaoVariavel) transacao;
                ps.setString(8, "VARIAVEL");
                ps.setString(9, null);
                ps.setInt(10, variavel.isParcelado() ? 1 : 0);
                ps.setInt(11, variavel.getNumeroParcelas());
            } else {
                throw new IllegalArgumentException("Tipo de transação não reconhecido.");
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar transação: " + e.getMessage(), e);
        }
    }

    public boolean removerTransacao(String emailUsuario, int id) {
        String sql = "DELETE FROM transacoes WHERE email_usuario = ? AND id = ?;";

        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarEmail(emailUsuario));
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover transação: " + e.getMessage(), e);
        }
    }

    private Transacao criarTransacaoAPartirDoResultado(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String descricao = rs.getString("descricao");
        double valor = rs.getDouble("valor");
        Categoria categoria = Categoria.valueOf(rs.getString("categoria"));
        LocalDate data = LocalDate.parse(rs.getString("data"));
        String metodoPagamento = rs.getString("metodo_pagamento");
        String tipo = rs.getString("tipo");

        if ("FIXA".equals(tipo)) {
            TransacaoFixa.NaturezaFixa natureza = TransacaoFixa.NaturezaFixa.valueOf(rs.getString("natureza"));
            return new TransacaoFixa(id, descricao, valor, categoria, data, metodoPagamento, natureza);
        }

        boolean parcelado = rs.getInt("parcelado") == 1;
        int numeroParcelas = rs.getInt("numero_parcelas");
        return new TransacaoVariavel(id, descricao, valor, categoria, data, metodoPagamento, parcelado, numeroParcelas);
    }

    private String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}
