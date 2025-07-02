package com.consultas.app;

import javax.swing.*;
import java.awt.*;

public class ClienteFormPanel extends JPanel {

    private JTextField nomeField, telefoneField, emailField, cpfField;

    public ClienteFormPanel() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos
        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        add(telefoneField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("CPF (somente números):"));
        cpfField = new JTextField();
        add(cpfField);

        // Botão
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarCliente());
        add(new JLabel()); // espaço vazio
        add(salvarButton);
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
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!\nID: " + novoId);
                nomeField.setText("");
                telefoneField.setText("");
                emailField.setText("");
                cpfField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: verifique os dados.\n" + ex.getMessage());
        }
    }
}
