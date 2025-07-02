package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultaListFrame extends JFrame {

    public ConsultaListFrame() {
        setTitle("Consultas Cadastradas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ConsultaDAO dao = new ConsultaDAO();
        List<Consulta> consultas = dao.listarTodas();

        JTextArea area = new JTextArea();
        area.setEditable(false);

        if (consultas.isEmpty()) {
            area.setText("Nenhuma consulta cadastrada.");
        } else {
            for (Consulta c : consultas) {
                area.append(formatarConsulta(c));
                area.append("\n--------------------------\n");
            }
        }

        add(new JScrollPane(area), BorderLayout.CENTER);
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
