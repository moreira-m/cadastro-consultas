package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaFormPanel extends JPanel {

    private JTextField clientIdField, dataField, horarioField, medicoField, observacoesField;

    public ConsultaFormPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("ID do Cliente:"));
        clientIdField = new JTextField();
        add(clientIdField);

        add(new JLabel("Data (YYYY-MM-DD):"));
        dataField = new JTextField();
        add(dataField);

        add(new JLabel("Horário (HH:MM):"));
        horarioField = new JTextField();
        add(horarioField);

        add(new JLabel("Médico:"));
        medicoField = new JTextField();
        add(medicoField);

        add(new JLabel("Observações:"));
        observacoesField = new JTextField();
        add(observacoesField);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarConsulta());
        add(new JLabel()); // espaço vazio
        add(salvarButton);
    }

    private void salvarConsulta() {
        try {
            int clientId = Integer.parseInt(clientIdField.getText());
            LocalDate data = LocalDate.parse(dataField.getText());
            LocalTime horario = LocalTime.parse(horarioField.getText());
            String medico = medicoField.getText();
            String obs = observacoesField.getText();

            Consulta consulta = new Consulta(clientId, data, horario, medico, obs);
            ConsultaDAO dao = new ConsultaDAO();

            if (dao.inserir(consulta)) {
                JOptionPane.showMessageDialog(this, "Consulta cadastrada com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar consulta.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: verifique os dados.\n" + ex.getMessage());
        }
    }

    private void limparCampos() {
        clientIdField.setText("");
        dataField.setText("");
        horarioField.setText("");
        medicoField.setText("");
        observacoesField.setText("");
    }
}
