package com.consultas.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {

    // ---------- Campos ----------
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel   contentPanel = new JPanel(cardLayout);

    // ---------- Construtor ----------
    public DashboardFrame() {
        setTitle("Sistema de Consultas");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ---------------- Menu superior ---------------- */
        JPanel menuPanel = criarMenuSuperior();
        add(menuPanel, BorderLayout.NORTH);

        /* ------------ Painéis (cards) ------------ */
        adicionarCard("clienteForm",   wrap(new ClienteFormPanel()));
        adicionarCard("clienteList",   wrap(new ClienteListPanel()));
        adicionarCard("consultaForm",  wrap(new ConsultaFormPanel()));
        adicionarCard("consultaList",  wrap(new ConsultaListPanel()));

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /* =========================================================
       ================  MÉTODOS AUXILIARES  ====================
       ========================================================= */

    /** Cria a barra de botões no topo */
    private JPanel criarMenuSuperior() {
        Font menuFont  = new Font("SansSerif", Font.PLAIN, 12);
        Dimension btnS = new Dimension(140, 26);
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton btnCadCli   = novoBotao("Cadastrar Cliente", btnS, menuFont,
                              e -> substituirCard("clienteForm", new ClienteFormPanel()));
        JButton btnListCli  = novoBotao("Listar Clientes",   btnS, menuFont,
                              e -> substituirCard("clienteList", new ClienteListPanel()));
        JButton btnNovaCon  = novoBotao("Nova Consulta",     btnS, menuFont,
                              e -> substituirCard("consultaForm", new ConsultaFormPanel()));
        JButton btnListCon  = novoBotao("Listar Consultas",  btnS, menuFont,
                              e -> substituirCard("consultaList", new ConsultaListPanel()));

        JButton btnBusConCPF = novoBotao("Buscar Consulta por CPF", btnS, menuFont,
                              e -> buscarConsultaPorCPF());

        JButton btnBusCliCPF = novoBotao("Buscar Cliente por CPF",  btnS, menuFont,
                              e -> buscarClientePorCPF());

        JButton btnDelCon   = novoBotao("Excluir Consulta por ID",  btnS, menuFont,
                              e -> excluirConsultaPorId());

        bar.add(btnCadCli);  bar.add(btnListCli);  bar.add(btnNovaCon);
        bar.add(btnListCon); bar.add(btnBusConCPF); bar.add(btnBusCliCPF); bar.add(btnDelCon);
        return bar;
    }

    /** Cria botão já estilizado e com ActionListener */
    private JButton novoBotao(String txt, Dimension dim, Font f, java.awt.event.ActionListener al) {
        JButton b = new JButton(txt);
        b.setPreferredSize(dim);
        b.setFont(f);
        b.addActionListener(al);
        return b;
    }

    /** Envolve qualquer painel num wrapper alinhado no canto superior-esquerdo */
    private JPanel wrap(JPanel inner) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(inner, BorderLayout.NORTH);          // cola topo-esquerda
        wrapper.setBackground(new Color(245,245,245));   // cor de fundo suave
        wrapper.setName(inner.getClass().getSimpleName());
        return wrapper;
    }

    /** Adiciona um card novo ao contentPanel */
    private void adicionarCard(String nome, JPanel painel) {
        painel.setName(nome);
        contentPanel.add(painel, nome);
    }

    /** Substitui (ou cria) um card mantendo alinhamento norte-esquerda */
    private void substituirCard(String nome, JPanel novoInner) {
        // remove se existir
        for (Component comp : contentPanel.getComponents()) {
            if (nome.equals(comp.getName())) {
                contentPanel.remove(comp);
                break;
            }
        }
        adicionarCard(nome, wrap(novoInner));
        cardLayout.show(contentPanel, nome);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /* =========================================================
       =================  AÇÕES DE BOTÕES  =====================
       ========================================================= */

    private void buscarConsultaPorCPF() {
        String in = JOptionPane.showInputDialog(this,"Digite o CPF do cliente:");
        if (in == null) return;
        try {
            long cpf = Long.parseLong(in.trim());
            ClienteDAO cDAO = new ClienteDAO();
            Cliente    cli  = cDAO.buscarPorCPF(cpf);

            if (cli == null) { JOptionPane.showMessageDialog(this,"Cliente não encontrado."); return; }

            ConsultaDAO qDAO = new ConsultaDAO();
            List<Consulta> cons = qDAO.buscarPorClienteId(cli.getId());

            if (cons.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Esse cliente não possui consultas agendadas.");
                return;
            }
            StringBuilder sb = new StringBuilder("Cliente: ").append(cli.getNome()).append("\n\n");
            for (Consulta c : cons) sb.append(formatarConsulta(c)).append("\n--------------------------\n");
            JOptionPane.showMessageDialog(this, sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"CPF inválido.");
        }
    }

    private void buscarClientePorCPF() {
        String in = JOptionPane.showInputDialog(this,"Digite o CPF do cliente:");
        if (in == null) return;
        try {
            long cpf = Long.parseLong(in.trim());
            Cliente cli = new ClienteDAO().buscarPorCPF(cpf);
            JOptionPane.showMessageDialog(this,
                    (cli != null) ? "Cliente encontrado:\n"+formatarCliente(cli)
                                  : "Cliente não encontrado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"CPF inválido.");
        }
    }

    private void excluirConsultaPorId() {
        String in = JOptionPane.showInputDialog(this,"Digite o ID da consulta a excluir:");
        if (in == null) return;
        try {
            int id = Integer.parseInt(in.trim());
            boolean ok = new ConsultaDAO().excluir(id);
            JOptionPane.showMessageDialog(this, ok ? "Consulta excluída com sucesso."
                                                   : "Consulta não encontrada.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"ID inválido.");
        }
    }

    /* =========================================================
       =====================  FORMATAÇÕES  =====================
       ========================================================= */

    private String formatarConsulta(Consulta c) {
        return "ID: " + c.getConsultaId() +
               "\nCliente ID: " + c.getClientId() +
               "\nData: " + c.getData() +
               "\nHorário: " + c.getHorario() +
               "\nMédico: " + c.getMedico() +
               "\nObservações: " + c.getObservacoes();
    }

    private String formatarCliente(Cliente c) {
        return "ID: " + c.getId() +
               "\nNome: " + c.getNome() +
               "\nTelefone: " + c.getTelefone() +
               "\nEmail: " + c.getEmail() +
               "\nCPF: " + c.getCPF();
    }

    /* ===================  MAIN LAUNCHER  ==================== */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardFrame::new);
    }
}
