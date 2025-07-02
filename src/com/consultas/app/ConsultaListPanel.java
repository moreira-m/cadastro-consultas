package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultaListPanel extends JPanel {

    public ConsultaListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ConsultaDAO consultaDAO = new ConsultaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Consulta> consultas = consultaDAO.listarTodas();

        JTextArea area = new JTextArea();
        area.setEditable(false);

        if (consultas.isEmpty()) {
            area.setText("Nenhuma consulta cadastrada.");
        } else {
            for (Consulta c : consultas) {
                Cliente cliente = clienteDAO.buscarPorId(c.getClientId());
                area.append(formatarConsulta(c, cliente));
                area.append("\n--------------------------\n");
            }
        }

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    private String formatarConsulta(Consulta c, Cliente cliente) {
        String cpfInfo = (cliente != null) ? String.valueOf(cliente.getCPF()) : "Desconhecido";
        String nome = (cliente != null) ? cliente.getNome() : "Desconhecido";

        return "Consulta ID: " + c.getConsultaId() +
               "\nCliente ID: " + c.getClientId() +
               "\nNome: " + nome +
               "\nCPF: " + cpfInfo +
               "\nData: " + c.getData() +
               "\nHorário: " + c.getHorario() +
               "\nMédico: " + c.getMedico() +
               "\nObservações: " + c.getObservacoes();
    }
}
