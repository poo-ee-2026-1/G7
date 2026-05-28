package src;

import javax.swing.*;
import model.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Falha ao definir o visual nativo; continua com o visual padrão
        }

        SwingUtilities.invokeLater(() -> {
            Usuario usuario = Usuario.realizarLogin();

            if (usuario != null) {
                GerenciadorFinanceiro gerenciador = new GerenciadorFinanceiro(usuario);
                InterfaceGrafica interfaceGrafica = new InterfaceGrafica(gerenciador);
                interfaceGrafica.setVisible(true);
                System.out.println("Sistema iniciado para: " + usuario.getNome());
            } else {
                System.out.println("Login cancelado. Encerrando programa.");
                System.exit(0);
            }
        });
    }
}