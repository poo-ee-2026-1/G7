package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Usuario {

    private String nome;
    private String email;

    public Usuario(String nome, String email) {
        this.nome  = nome;
        this.email = email;
    }

    public static Usuario realizarLogin() {
        Color COLOR_BG = new Color(18, 18, 23);
        Color COLOR_TEXT = new Color(240, 240, 240);
        Color COLOR_BORDER = new Color(45, 45, 50);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lbNome = new JLabel("Seu Nome:");
        lbNome.setForeground(COLOR_TEXT);
        JTextField nomeField = new JTextField(15);
        nomeField.setBackground(new Color(28, 28, 33));
        nomeField.setForeground(COLOR_TEXT);
        nomeField.setBorder(new LineBorder(COLOR_BORDER));

        JLabel lbEmail = new JLabel("Seu E-mail:");
        lbEmail.setForeground(COLOR_TEXT);
        JTextField emailField = new JTextField(15);
        emailField.setBackground(new Color(28, 28, 33));
        emailField.setForeground(COLOR_TEXT);
        emailField.setBorder(new LineBorder(COLOR_BORDER));

        panel.add(lbNome);
        panel.add(nomeField);
        panel.add(lbEmail);
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                "Login - Controle Financeiro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                return realizarLogin();
            }
            return new Usuario(nome, email);
        }
        return null;
    }

    public String getNome()  { return nome; }
    public String getEmail() { return email; }

    public void setNome(String nome)   { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Usuário: " + nome + " <" + email + ">";
    }
}
