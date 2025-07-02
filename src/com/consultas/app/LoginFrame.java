package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setLocationRelativeTo(null); // centraliza na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Componentes
        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioField = new JTextField();

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField();

        loginButton = new JButton("Entrar");
        statusLabel = new JLabel("");

        // Ação do botão
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        // Adiciona ao layout
        add(usuarioLabel);
        add(usuarioField);
        add(senhaLabel);
        add(senhaField);
        add(new JLabel()); // espaço vazio
        add(loginButton);
        add(statusLabel);

        setVisible(true);
    }

    private void autenticar() {
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());

        LoginDAO loginDAO = new LoginDAO();
        boolean autenticado = loginDAO.autenticar(usuario, senha);

        if (autenticado) {
            statusLabel.setText("Login bem-sucedido!");
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario + "!");
            this.dispose();

            // Aqui você pode abrir o DashboardFrame depois
            // new DashboardFrame();

        } else {
            statusLabel.setText("Usuário ou senha inválidos.");
        }
    }

    // Método main para testes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
