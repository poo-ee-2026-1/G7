package src;

import model.*;
import javax.swing.*;

/**
 * Ponto de entrada do programa.
 * Utiliza o método de login integrado na classe Usuario para respeitar o limite de classes.
 */
public class Main {

    public static void main(String[] args) {
        // Define o visual nativo do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Chama o login diretamente da classe Usuario (Unificação de classes)
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
