package com.consultas.app;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private ClienteFormPanel clienteForm;
    private ConsultaFormPanel consultaForm;

    public DashboardFrame() {
        setTitle("Painel Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painéis fixos
        clienteForm = new ClienteFormPanel();
        consultaForm = new ConsultaFormPanel();

        // Painel de botões à esquerda
        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        JButton btnListarClientes = new JButton("Listar Clientes");
        JButton btnNovaConsulta = new JButton("Nova Consulta");
        JButton btnListarConsultas = new JButton("Listar Consultas");
        JButton btnBuscarConsulta = new JButton("Buscar Consulta por ID");
        JButton btnExcluirConsulta = new JButton("Excluir Consulta por ID");

        menuPanel.add(btnCadastrarCliente);
        menuPanel.add(btnListarClientes);
        menuPanel.add(btnNovaConsulta);
        menuPanel.add(btnListarConsultas);
        menuPanel.add(btnBuscarConsulta);
        menuPanel.add(btnExcluirConsulta);

        add(menuPanel, BorderLayout.WEST);

        // Painel central com CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Painéis iniciais
        contentPanel.add(clienteForm, "clienteForm");
        contentPanel.add(new ClienteListPanel(), "clienteList");
        contentPanel.add(consultaForm, "consultaForm");
        contentPanel.add(new ConsultaListPanel(), "consultaList");

        add(contentPanel, BorderLayout.CENTER);

        // Ações dos botões
        btnCadastrarCliente.addActionListener(e -> {
            clienteForm = new ClienteFormPanel();
            substituirPainel("clienteForm", clienteForm);
        });

        btnListarClientes.addActionListener(e -> {
            ClienteListPanel updated = new ClienteListPanel();
            substituirPainel("clienteList", updated);
        });

        btnNovaConsulta.addActionListener(e -> {
            consultaForm = new ConsultaFormPanel();
            substituirPainel("consultaForm", consultaForm);
        });

        btnListarConsultas.addActionListener(e -> {
            ConsultaListPanel updated = new ConsultaListPanel();
            substituirPainel("consultaList", updated);
        });

        btnBuscarConsulta.addActionListener(e -> {
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

        btnExcluirConsulta.addActionListener(e -> {
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

        setVisible(true);
    }

    private void substituirPainel(String nome, JPanel novoPainel) {
        contentPanel.remove(novoPainel); // remove se já existir
        contentPanel.add(novoPainel, nome);
        cardLayout.show(contentPanel, nome);
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
