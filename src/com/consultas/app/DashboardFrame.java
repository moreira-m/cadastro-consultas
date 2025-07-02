package com.consultas.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Painel Principal");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Botões
        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        JButton btnListarClientes = new JButton("Listar Clientes");
        JButton btnNovaConsulta = new JButton("Nova Consulta");
        JButton btnBuscarConsulta = new JButton("Buscar Consulta por ID");
        JButton btnExcluirConsulta = new JButton("Excluir Consulta por ID");
        JButton btnListarConsultas = new JButton("Listar Todas as Consultas");

        // Ações
        btnCadastrarCliente.addActionListener(e -> new ClienteForm());
        btnListarClientes.addActionListener(e -> new ClienteListFrame());
        btnNovaConsulta.addActionListener((ActionEvent e) -> new ConsultaForm());

        btnBuscarConsulta.addActionListener((ActionEvent e) -> {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID da consulta:");
            try {
                int id = Integer.parseInt(idStr);
                ConsultaDAO dao = new ConsultaDAO();
                Consulta consulta = dao.buscarPorId(id);
                if (consulta != null) {
                    JOptionPane.showMessageDialog(this, "Consulta encontrada:\n" + formatarConsulta(consulta));
                } else {
                    JOptionPane.showMessageDialog(this, "Consulta não encontrada.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnExcluirConsulta.addActionListener((ActionEvent e) -> {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID da consulta a excluir:");
            try {
                int id = Integer.parseInt(idStr);
                ConsultaDAO dao = new ConsultaDAO();
                boolean ok = dao.excluir(id);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Consulta excluída com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Consulta não encontrada.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnListarConsultas.addActionListener((ActionEvent e) -> new ConsultaListFrame());

        // Adiciona ao layout
        add(btnCadastrarCliente);
        add(btnListarClientes);
        add(btnNovaConsulta);
        add(btnBuscarConsulta);
        add(btnExcluirConsulta);
        add(btnListarConsultas);

        setVisible(true);
    }

    private String formatarConsulta(Consulta c) {
        return "ID: " + c.getConsultaId() +
               "\nCliente ID: " + c.getClientId() +
               "\nData: " + c.getData() +
               "\nHorário: " + c.getHorario() +
               "\nMédico: " + c.getMedico() +
               "\nObservações: " + c.getObservacoes();
    }
}
