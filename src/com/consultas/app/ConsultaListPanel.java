package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultaListPanel extends JPanel {

    public ConsultaListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
