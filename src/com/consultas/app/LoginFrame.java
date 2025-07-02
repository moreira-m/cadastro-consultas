package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginFrame extends JFrame {

    private final JTextField usuarioField = new JTextField(15);
    private final JPasswordField senhaField = new JPasswordField(15);
    private final JButton loginButton = new JButton("Entrar");
    private final JLabel statusLabel = new JLabel("");

    public LoginFrame() {
        setTitle("Login - Sistema de Consultas");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel backgroundLabel = criarBackground();
        backgroundLabel.setLayout(new GridBagLayout());
        setContentPane(backgroundLabel);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.LINE_END;

        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioLabel.setForeground(Color.WHITE);
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(usuarioLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        form.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        form.add(senhaLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        form.add(senhaField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        form.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        form.add(statusLabel, gbc);

        backgroundLabel.add(form, new GridBagConstraints());

        loginButton.addActionListener(e -> autenticar());

        setVisible(true);
    }

    private JLabel criarBackground() {
        URL imgURL = getClass().getResource("/imagens/login-bg.png");
        if (imgURL != null) {
            ImageIcon bg = new ImageIcon(imgURL);
            Image img = bg.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } else {
            JLabel lbl = new JLabel();
            lbl.setOpaque(true);
            lbl.setBackground(Color.LIGHT_GRAY);
            return lbl;
        }
    }

    private void autenticar() {
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());

        boolean ok = new LoginDAO().autenticar(usuario, senha);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario + "!");
            dispose();
            new DashboardFrame();
        } else {
            statusLabel.setText("Usuário ou senha inválidos");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
