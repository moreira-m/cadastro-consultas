package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClienteListFrame extends JFrame {

    public ClienteListFrame() {
        setTitle("Clientes Cadastrados");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        setVisible(true);
    }

    private String formatarCliente(Cliente c) {
        return "ID: " + c.getId() +
               "\nNome: " + c.getNome() +
               "\nTelefone: " + c.getTelefone() +
               "\nEmail: " + c.getEmail() +
               "\nCPF: " + c.getCPF();
    }
}
