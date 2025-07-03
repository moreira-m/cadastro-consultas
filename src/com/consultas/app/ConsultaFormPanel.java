package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.Date;

public class ConsultaFormPanel extends JPanel {

    private JTextField cpfField, medicoField, observacoesField;
    private JSpinner dataSpinner, horaSpinner;

    public ConsultaFormPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        Font small = new Font("SansSerif", Font.PLAIN, 12);
        Dimension fieldSize = new Dimension(200, 22);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("CPF do Cliente:"), gbc);
        cpfField = novoCampo(fieldSize, small);
        gbc.gridx = 1;
        add(cpfField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Data:"), gbc);
        dataSpinner = new JSpinner(new SpinnerDateModel());
        dataSpinner.setPreferredSize(fieldSize);
        JSpinner.DateEditor dataEditor = new JSpinner.DateEditor(dataSpinner, "dd/MM/yyyy");
        dataSpinner.setEditor(dataEditor);
        dataSpinner.setFont(small);
        gbc.gridx = 1;
        add(dataSpinner, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Horário:"), gbc);
        horaSpinner = new JSpinner(new SpinnerDateModel());
        horaSpinner.setPreferredSize(fieldSize);
        JSpinner.DateEditor horaEditor = new JSpinner.DateEditor(horaSpinner, "HH:mm");
        horaSpinner.setEditor(horaEditor);
        horaSpinner.setFont(small);
        gbc.gridx = 1;
        add(horaSpinner, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Médico:"), gbc);
        medicoField = novoCampo(fieldSize, small);
        gbc.gridx = 1;
        add(medicoField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Observações:"), gbc);
        observacoesField = novoCampo(fieldSize, small);
        gbc.gridx = 1;
        add(observacoesField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.setPreferredSize(new Dimension(100, 26));
        salvarBtn.setFont(small);
        salvarBtn.addActionListener(e -> salvarConsulta());
        add(salvarBtn, gbc);
    }

    private JTextField novoCampo(Dimension d, Font f) {
        JTextField t = new JTextField();
        t.setPreferredSize(d);
        t.setFont(f);
        return t;
    }

    private void limpar() {
        cpfField.setText("");
        medicoField.setText("");
        observacoesField.setText("");
    }

    private void salvarConsulta() {
        try {
            long cpf = Long.parseLong(cpfField.getText().trim());
            ClienteDAO cliDAO = new ClienteDAO();
            Cliente cli = cliDAO.buscarPorCPF(cpf);
            if (cli == null) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado");
                return;
            }

            ZoneId zone = ZoneId.systemDefault();
            Date d = (Date) dataSpinner.getValue();
            Date h = (Date) horaSpinner.getValue();
            LocalDate data = d.toInstant().atZone(zone).toLocalDate();
            LocalTime hora = h.toInstant().atZone(zone).toLocalTime().withSecond(0).withNano(0);

            String medico = medicoField.getText().trim();
            String obs = observacoesField.getText();

            ConsultaDAO conDAO = new ConsultaDAO();
            if (conDAO.horarioOcupado(data, hora, medico)) {
                String dataFormatada = data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                JOptionPane.showMessageDialog(this, "O médico \"" + medico + "\" já possui consulta em " + dataFormatada + " às " + hora + ".");
                return;
            }

            Consulta c = new Consulta(cli.getId(), data, hora, medico, obs);
            if (conDAO.inserir(c)) {
                JOptionPane.showMessageDialog(this, "Consulta cadastrada");
                limpar();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar consulta");
            }

        } catch (Exception ex) {
        }
    }
}
