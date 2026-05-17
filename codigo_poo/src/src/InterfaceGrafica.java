package src;

import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InterfaceGrafica extends JFrame {

    private GerenciadorFinanceiro gerenciador;

    // Paleta de cores
    private final Color COLOR_BG          = new Color(18, 18, 23);
    private final Color COLOR_SURFACE     = new Color(28, 28, 33);
    private final Color COLOR_ACCENT      = new Color(46, 204, 113);  // Verde esmeralda
    private final Color COLOR_TEXT        = new Color(240, 240, 240);
    private final Color COLOR_TEXT_DIM    = new Color(160, 160, 170);
    private final Color COLOR_BORDER      = new Color(45, 45, 50);

    private JTextField descricaoField;
    private JTextField valorField;
    private JTextField metodoPagamentoField;
    private JComboBox<Categoria> categoriaComboBox;
    private JComboBox<String> tipoTransacaoComboBox;
    private JTextField dataField;
    private JCheckBox parceladoCheckBox;
    private JTextField numParcelasField;
    private JTextArea displayArea;

    public InterfaceGrafica(GerenciadorFinanceiro gerenciador) {
        this.gerenciador = gerenciador;
        setTitle("Controle Financeiro - " + gerenciador.getUsuario().getNome());
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));

        // ── Painel Esquerdo (Formulário) ─────────────────────────────────────
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_BG);
        leftPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        leftPanel.setPreferredSize(new Dimension(400, 0));

        JLabel titleLabel = new JLabel("Novo Registro");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("Preencha os campos abaixo.");
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subTitleLabel.setForeground(COLOR_TEXT_DIM);
        subTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(subTitleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Campos do Formulário
        tipoTransacaoComboBox = createStyledComboBox(new String[]{"Receita Fixa", "Despesa Fixa", "Despesa Variável"});
        addFormField(leftPanel, "Tipo", tipoTransacaoComboBox);

        dataField = createStyledTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        addFormField(leftPanel, "Data", dataField);

        categoriaComboBox = createStyledComboBox(Categoria.values());
        addFormField(leftPanel, "Categoria *", categoriaComboBox);

        descricaoField = createStyledTextField("");
        descricaoField.setToolTipText("Ex.: Supermercado");
        addFormField(leftPanel, "Descrição *", descricaoField);

        valorField = createStyledTextField("");
        valorField.setToolTipText("Ex.: 150,00");
        addFormField(leftPanel, "Valor (R$) *", valorField);

        metodoPagamentoField = createStyledTextField("");
        metodoPagamentoField.setToolTipText("Ex.: Pix, Cartão, Dinheiro");
        addFormField(leftPanel, "Método de pagamento", metodoPagamentoField);

        // Parcelamento
        JPanel parcelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        parcelPanel.setBackground(COLOR_BG);
        parcelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        parceladoCheckBox = new JCheckBox("Parcelado");
        parceladoCheckBox.setBackground(COLOR_BG);
        parceladoCheckBox.setForeground(COLOR_TEXT);
        numParcelasField = createStyledTextField("1");
        numParcelasField.setPreferredSize(new Dimension(60, 35));
        numParcelasField.setEnabled(false);
        parceladoCheckBox.addActionListener(e -> numParcelasField.setEnabled(parceladoCheckBox.isSelected()));
        parcelPanel.add(parceladoCheckBox);
        parcelPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        parcelPanel.add(numParcelasField);
        leftPanel.add(parcelPanel);
        leftPanel.add(Box.createVerticalGlue());

        // Botões de Ação
        JPanel actionButtons = new JPanel(new GridLayout(1, 2, 15, 0));
        actionButtons.setBackground(COLOR_BG);
        actionButtons.setMaximumSize(new Dimension(400, 50));
        actionButtons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnCancel = createStyledButton("Cancelar");
        btnCancel.addActionListener(e -> limparCampos());
        JButton btnSave = createStyledButton("Salvar");
        btnSave.addActionListener(e -> adicionarTransacao());

        actionButtons.add(btnCancel);
        actionButtons.add(btnSave);
        leftPanel.add(actionButtons);

        add(leftPanel, BorderLayout.WEST);

        // ── Painel Direito (Histórico e Resumo) ──────────────────────────────
        JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
        rightPanel.setBackground(COLOR_SURFACE);
        rightPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        displayArea = new JTextArea();
        displayArea.setBackground(COLOR_SURFACE);
        displayArea.setForeground(COLOR_TEXT);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new LineBorder(COLOR_BORDER, 1, true));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(COLOR_SURFACE);

        JButton btnList    = createStyledButton("Atualizar Lista");
        btnList.addActionListener(e -> listarTransacoes());
        JButton btnSummary = createStyledButton("Ver Resumo");
        btnSummary.addActionListener(e -> exibirResumoGeral());
        JButton btnDelete  = createStyledButton("Remover por ID");
        btnDelete.addActionListener(e -> removerTransacao());

        navPanel.add(btnList);
        navPanel.add(btnSummary);
        navPanel.add(btnDelete);
        rightPanel.add(navPanel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);

        listarTransacoes();
    }

    // ── Helpers de UI ─────────────────────────────────────────────────────────

    private void addFormField(JPanel parent, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(COLOR_TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
        parent.add(Box.createRigidArea(new Dimension(0, 8)));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, 45));
        parent.add(field);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.setBackground(COLOR_SURFACE);
        field.setForeground(COLOR_TEXT);
        field.setCaretColor(COLOR_ACCENT);
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
        return field;
    }

    /**
     * ComboBox com fundo preto, letras brancas e itens da lista com
     * hover verde ao passar o cursor (via ListCellRenderer customizado).
     */
    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        final Color BG    = new Color(18, 18, 23);
        final Color FG    = new Color(240, 240, 240);
        final Color GREEN = new Color(46, 204, 113);

        // Subclasse anônima: sobrescreve paintComponent para garantir fundo
        // preto mesmo quando o L&F do Windows tenta pintar por cima.
        JComboBox<T> combo = new JComboBox<T>(items) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(new Color(45, 45, 50));
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };

        // BasicComboBoxUI com fundo e seta totalmente customizados.
        // paintCurrentValueBackground é o método que o Swing usa para pintar
        // o fundo da área do item selecionado — ele buscava a cor no UIManager
        // e pintava branco por cima de tudo. Sobrescrevendo-o, forçamos preto.
        combo.setUI(new BasicComboBoxUI() {
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(BG);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
            @Override
            protected JButton createArrowButton() {
                JButton arrow = new JButton("▼");
                arrow.setFont(new Font("SansSerif", Font.PLAIN, 11));
                arrow.setBackground(BG);
                arrow.setForeground(FG);
                arrow.setBorderPainted(false);
                arrow.setFocusPainted(false);
                arrow.setContentAreaFilled(true);
                arrow.setOpaque(true);
                return arrow;
            }
        });

        combo.setOpaque(true);
        combo.setBackground(BG);
        combo.setForeground(FG);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 15));

        // Renderer: closed state (index = -1) e lista suspensa
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
                lbl.setBorder(new EmptyBorder(6, 12, 6, 12));
                lbl.setOpaque(true);

                // index == -1 → item exibido no campo fechado (sempre preto)
                // index >= 0  → itens da lista: verde no hover, preto fora
                if (isSelected && index >= 0) {
                    lbl.setBackground(GREEN);
                    lbl.setForeground(BG);
                } else {
                    lbl.setBackground(BG);
                    lbl.setForeground(FG);
                }
                return lbl;
            }
        });

        return combo;
    }

    /**
     * Todos os botões: fundo verde-água, texto preto.
     * Hover: verde mais escuro.
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);

        // Força o renderizador básico do Swing para que setBackground()
        // funcione corretamente no Windows (o L&F do sistema ignora a cor).
        btn.setUI(new BasicButtonUI());

        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBackground(new Color(46, 204, 113));   // #2ECC71 — verde-água
        btn.setForeground(new Color(18, 18, 23));      // texto preto
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        // Hover: escurece para #21A358
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(33, 163, 88)); // #21A358
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(46, 204, 113)); // #2ECC71
            }
        });

        return btn;
    }

    // ── Lógica de negócio ─────────────────────────────────────────────────────

    private void adicionarTransacao() {
        // Validação da descrição
        String descricao = descricaoField.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "A descrição é obrigatória.",
                    "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação do valor
        String valorTexto = valorField.getText().replace(",", ".").trim();
        double valor;
        try {
            valor = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Valor inválido. Use apenas números (ex.: 150,00).",
                    "Erro de formato",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (valor <= 0) {
            JOptionPane.showMessageDialog(this,
                    "O valor deve ser maior que zero.",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação da data
        LocalDate data;
        try {
            data = LocalDate.parse(dataField.getText().trim(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida. Use o formato dd/MM/yyyy (ex.: 25/12/2025).",
                    "Erro de formato",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criação e registro da transação
        try {
            Categoria categoria    = (Categoria) categoriaComboBox.getSelectedItem();
            String tipoSelecionado = (String) tipoTransacaoComboBox.getSelectedItem();
            String metodo          = metodoPagamentoField.getText().trim();

            switch (tipoSelecionado) {
                case "Receita Fixa":
                    gerenciador.adicionarTransacao(new TransacaoFixa(
                            descricao, valor, categoria, data, metodo,
                            TransacaoFixa.NaturezaFixa.RECEITA));
                    break;
                case "Despesa Fixa":
                    gerenciador.adicionarTransacao(new TransacaoFixa(
                            descricao, valor, categoria, data, metodo,
                            TransacaoFixa.NaturezaFixa.DESPESA));
                    break;
                case "Despesa Variável":
                    boolean parcelado  = parceladoCheckBox.isSelected();
                    int numParcelas    = parcelado
                            ? Integer.parseInt(numParcelasField.getText().trim())
                            : 1;
                    gerenciador.adicionarTransacao(new TransacaoVariavel(
                            descricao, valor, categoria, data, metodo,
                            parcelado, numParcelas));
                    break;
            }

            JOptionPane.showMessageDialog(this, "Registro salvo com sucesso!");
            limparCampos();
            listarTransacoes();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro inesperado ao salvar: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTransacoes() {
        displayArea.setText("--- HISTÓRICO DE TRANSAÇÕES ---\n\n");
        if (gerenciador.listarTodas().isEmpty()) {
            displayArea.append("Nenhum registro cadastrado.");
        } else {
            gerenciador.listarTodas().forEach(t -> displayArea.append(t.toString() + "\n"));
        }
    }

    private void exibirResumoGeral() {
        displayArea.setText("--- RESUMO FINANCEIRO ---\n\n");
        displayArea.append(String.format("SALDO ATUAL     : R$ %.2f\n", gerenciador.calcularSaldo()));
        displayArea.append(String.format("TOTAL RECEITAS  : R$ %.2f\n", gerenciador.calcularTotalReceitas()));
        displayArea.append(String.format("TOTAL DESPESAS  : R$ %.2f\n", gerenciador.calcularTotalDespesas()));
    }

    private void removerTransacao() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID para remover:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                if (gerenciador.removerTransacao(Integer.parseInt(idStr.trim()))) {
                    listarTransacoes();
                } else {
                    JOptionPane.showMessageDialog(this, "ID não encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.");
            }
        }
    }

    private void limparCampos() {
        descricaoField.setText("");
        valorField.setText("");
        metodoPagamentoField.setText("");
        categoriaComboBox.setSelectedIndex(0);
        tipoTransacaoComboBox.setSelectedIndex(0);
        dataField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        parceladoCheckBox.setSelected(false);
        numParcelasField.setText("1");
        numParcelasField.setEnabled(false);
    }
}