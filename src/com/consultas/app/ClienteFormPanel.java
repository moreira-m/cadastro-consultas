package com.consultas.app;

import javax.swing.*;
import java.awt.*;

public class ClienteFormPanel extends JPanel {

    private JTextField nomeField, telefoneField, emailField, cpfField;

    public ClienteFormPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        Font smallFont = new Font("SansSerif", Font.PLAIN, 12);
        Dimension fieldSize = new Dimension(200, 22);

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField();
        nomeField.setPreferredSize(fieldSize);
        nomeField.setFont(smallFont);
        gbc.gridx = 1;
        add(nomeField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Telefone:"), gbc);
        telefoneField = new JTextField();
        telefoneField.setPreferredSize(fieldSize);
        telefoneField.setFont(smallFont);
        gbc.gridx = 1;
        add(telefoneField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField();
        emailField.setPreferredSize(fieldSize);
        emailField.setFont(smallFont);
        gbc.gridx = 1;
        add(emailField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("CPF (somente números):"), gbc);
        cpfField = new JTextField();
        cpfField.setPreferredSize(fieldSize);
        cpfField.setFont(smallFont);
        gbc.gridx = 1;
        add(cpfField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton salvarButton = new JButton("Salvar");
        salvarButton.setPreferredSize(new Dimension(100, 26));
        salvarButton.setFont(smallFont);
        salvarButton.addActionListener(e -> salvarCliente());
        add(salvarButton, gbc);
    }

    private void salvarCliente() {
        try {
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String email = emailField.getText();
            long cpf = Long.parseLong(cpfField.getText());

            Cliente cliente = new Cliente(0, nome, telefone, email, cpf);
            ClienteDAO dao = new ClienteDAO();
            long novoId = dao.inserir(cliente);

            if (novoId != -1) {
                nomeField.setText("");
                telefoneField.setText("");
                emailField.setText("");
                cpfField.setText("");
            }
        } catch (Exception e) {
        }
    }
}
