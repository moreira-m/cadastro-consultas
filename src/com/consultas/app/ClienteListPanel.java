package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClienteListPanel extends JPanel {

    public ClienteListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.listarTodos();

        JTextArea area = new JTextArea();
        area.setEditable(false);

        if (clientes.isEmpty()) {
            area.setText("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                area.append(formatarCliente(c));
                area.append("\n--------------------------\n");
            }
        }

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    private String formatarCliente(Cliente c) {
        return "ID: " + c.getId() +
               "\nNome: " + c.getNome() +
               "\nTelefone: " + c.getTelefone() +
               "\nEmail: " + c.getEmail() +
               "\nCPF: " + c.getCPF();
    }
}
