package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaFormPanel extends JPanel {

    private JTextField cpfField, dataField, horarioField, medicoField, observacoesField;

    public ConsultaFormPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("CPF do Cliente:"));
        cpfField = new JTextField();
        add(cpfField);

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
            long cpf = Long.parseLong(cpfField.getText().trim());

            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorCPF(cpf);

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente com este CPF não foi encontrado.");
                return;
            }

            LocalDate data = LocalDate.parse(dataField.getText());
            LocalTime horario = LocalTime.parse(horarioField.getText());
            String medico = medicoField.getText();
            String obs = observacoesField.getText();

            Consulta consulta = new Consulta(cliente.getId(), data, horario, medico, obs);
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
        cpfField.setText("");
        dataField.setText("");
        horarioField.setText("");
        medicoField.setText("");
        observacoesField.setText("");
    }
}
