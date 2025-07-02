

import java.util.Scanner;

import src.com.consultas.app.Cliente;
import src.com.consultas.app.ClienteDAO;
import src.com.consultas.app.Consulta;
import src.com.consultas.app.ConsultaDAO;
import src.com.consultas.app.DatabaseConnection;
import src.com.consultas.app.LoginDAO;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ConsultaDAO consultaDAO = new ConsultaDAO();
    private static LoginDAO loginDAO = new LoginDAO();

    // Função auxiliar para repetir strings (compatível com Java 8+)
    private static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Testa conexão com banco
        if (DatabaseConnection.testConnection()) {
            System.out.println("Conexão com banco estabelecida!");
        } else {
            System.out.println("Falha na conexão com banco!");
            return;
        }

        // Realizar login
        if (!realizarLogin()) {
            System.out.println("Login falhado. Encerrando aplicação.");
            return;
        }

        // Menu principal
        boolean continuar = true;
        while (continuar) {
            exibirMenuPrincipal();
            int opcao = obterOpcao();
            
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuConsultas();
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Encerrando aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        
        scanner.close();
    }

    private static boolean realizarLogin() {
        System.out.println("=== LOGIN ===");
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        return loginDAO.autenticar(usuario, senha);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + repeat("=", 40));
        System.out.println("    SISTEMA DE CONSULTAS MÉDICAS");
        System.out.println(repeat("=", 40));
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Consultas");
        System.out.println("3. Sair");
        System.out.println(repeat("=", 40));
        System.out.print("Escolha uma opção: ");
    }

    private static void menuClientes() {
        boolean voltarMenuPrincipal = false;
        
        while (!voltarMenuPrincipal) {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            int opcao = obterOpcao();
            
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarCliente();
                    break;
                case 4:
                    atualizarCliente();
                    break;
                case 5:
                    excluirCliente();
                    break;
                case 6:
                    voltarMenuPrincipal = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuConsultas() {
        boolean voltarMenuPrincipal = false;
        
        while (!voltarMenuPrincipal) {
            System.out.println("\n=== MENU CONSULTAS ===");
            System.out.println("1. Agendar Consulta");
            System.out.println("2. Listar Consultas");
            System.out.println("3. Buscar Consulta");
            System.out.println("4. Atualizar Consulta");
            System.out.println("5. Cancelar Consulta");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            int opcao = obterOpcao();
            
            switch (opcao) {
                case 1:
                    agendarConsulta();
                    break;
                case 2:
                    listarConsultas();
                    break;
                case 3:
                    buscarConsulta();
                    break;
                case 4:
                    atualizarConsulta();
                    break;
                case 5:
                    cancelarConsulta();
                    break;
                case 6:
                    voltarMenuPrincipal = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n=== CADASTRAR CLIENTE ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("cpf: ");
        int cpf = scanner.nextInt();
        
        Cliente cliente = new Cliente(0, nome, telefone, email, cpf);
        
        if (clienteDAO.inserir(cliente)) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar cliente.");
        }
    }

    private static void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        List<Cliente> clientes = clienteDAO.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        System.out.println(repeat("-", 80));
        System.out.printf("%-5s %-30s %-15s %-30s%n", "ID", "Nome", "Telefone", "Email");
        System.out.println(repeat("-", 80));
        
        for (Cliente cliente : clientes) {
            System.out.printf("%-5d %-30s %-15s %-30s%n", 
                cliente.getId(), cliente.getNome(), 
                cliente.getTelefone(), cliente.getEmail());
        }
        System.out.println(repeat("-", 80));
    }

    private static void buscarCliente() {
        System.out.println("\n=== BUSCAR CLIENTE ===");
        System.out.print("Digite o ID do cliente: ");
        int id = obterOpcao();
        
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void atualizarCliente() {
        System.out.println("\n=== ATUALIZAR CLIENTE ===");
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int id = obterOpcao();
        
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        
        System.out.println("Cliente atual: " + cliente.getNome());
        System.out.print("Novo nome (ou Enter para manter): ");
        String nome = scanner.nextLine();
        if (!nome.trim().isEmpty()) {
            cliente.setNome(nome);
        }
        
        System.out.print("Novo telefone (ou Enter para manter): ");
        String telefone = scanner.nextLine();
        if (!telefone.trim().isEmpty()) {
            cliente.setTelefone(telefone);
        }
        
        System.out.print("Novo email (ou Enter para manter): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) {
            cliente.setEmail(email);
        }
        
        if (clienteDAO.atualizar(cliente)) {
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar cliente.");
        }
    }

    private static void excluirCliente() {
        System.out.println("\n=== EXCLUIR CLIENTE ===");
        System.out.print("Digite o ID do cliente a ser excluído: ");
        int id = obterOpcao();
        
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        
        System.out.println("Cliente: " + cliente.getNome());
        System.out.print("Confirma exclusão? (s/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.toLowerCase().startsWith("s")) {
            if (clienteDAO.excluir(id)) {
                System.out.println("Cliente excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir cliente.");
            }
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void agendarConsulta() {
        System.out.println("\n=== AGENDAR CONSULTA ===");
        
        // Listar clientes disponíveis
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        
        System.out.println("Clientes disponíveis:");
        System.out.println(repeat("-", 60));
        for (Cliente cliente : clientes) {
            System.out.printf("ID: %d - %s%n", cliente.getId(), cliente.getNome());
        }
        System.out.println(repeat("-", 60));
        
        System.out.print("Digite o ID do cliente: ");
        int clienteId = obterOpcao();
        
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        
        System.out.print("Data da consulta (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        LocalDate data = null;
        try {
            data = LocalDate.parse(dataStr);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
            return;
        }

        System.out.print("Hora da consulta (HH:MM): ");
        String horaStr = scanner.nextLine();
        LocalTime hora = null;
        try {
            hora = LocalTime.parse(horaStr);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de hora inválido. Use HH:MM.");
            return;
        }

        System.out.print("Médico: ");
        String medico = scanner.nextLine();
        System.out.print("Observações: ");
        String observacoes = scanner.nextLine();
        
        Consulta consulta = new Consulta(0, clienteId, data, hora, medico, observacoes);
        
        if (consultaDAO.inserir(consulta)) {
            System.out.println("Consulta agendada com sucesso!");
        } else {
            System.out.println("Erro ao agendar consulta.");
        }
    }

    private static void listarConsultas() {
        System.out.println("\n=== LISTA DE CONSULTAS ===");
        List<Consulta> consultas = consultaDAO.listarTodas();
        
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }
        
        System.out.println(repeat("-", 100));
        System.out.printf("%-5s %-15s %-12s %-8s %-20s %-30s%n", 
            "ID", "Cliente", "Data", "Hora", "Médico", "Observações");
        System.out.println(repeat("-", 100));
        
        for (Consulta consulta : consultas) {
            Cliente cliente = clienteDAO.buscarPorId(consulta.getClientId());
            String nomeCliente = (cliente != null) ? cliente.getNome() : "N/A";
            
            System.out.printf("%-5d %-15s %-12s %-8s %-20s %-30s%n",
                consulta.getConsultaId(), nomeCliente, consulta.getData(),
                consulta.getHorario(), consulta.getMedico(), consulta.getObservacoes());
        }
        System.out.println(repeat("-", 100));
    }

    private static void buscarConsulta() {
        System.out.println("\n=== BUSCAR CONSULTA ===");
        System.out.print("Digite o ID da consulta: ");
        int id = obterOpcao();
        
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta != null) {
            Cliente cliente = clienteDAO.buscarPorId(consulta.getClientId());
            
            System.out.println("Consulta encontrada:");
            System.out.println("ID: " + consulta.getConsultaId());
            System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "N/A"));
            System.out.println("Data: " + consulta.getData());
            System.out.println("Hora: " + consulta.getHorario());
            System.out.println("Médico: " + consulta.getMedico());
            System.out.println("Observações: " + consulta.getObservacoes());
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private static void atualizarConsulta() {
        System.out.println("\n=== ATUALIZAR CONSULTA ===");
        System.out.print("Digite o ID da consulta a ser atualizada: ");
        int id = obterOpcao();
        
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        
        Cliente cliente = clienteDAO.buscarPorId(consulta.getClientId());
        System.out.println("Consulta atual - Cliente: " + (cliente != null ? cliente.getNome() : "N/A"));
        
        System.out.print("Nova data (ou Enter para manter): ");
        String dataStr = scanner.nextLine();
        if (!dataStr.trim().isEmpty()) {
            try {
                consulta.setData(LocalDate.parse(dataStr));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use YYYY-MM-DD. Data não atualizada.");
            }
        }
        
        System.out.print("Nova hora (ou Enter para manter): ");
        String horaStr = scanner.nextLine();
        if (!horaStr.trim().isEmpty()) {
            try {
                consulta.setHorario(LocalTime.parse(horaStr));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de hora inválido. Use HH:MM. Hora não atualizada.");
            }
        }
        
        System.out.print("Novo médico (ou Enter para manter): ");
        String medico = scanner.nextLine();
        if (!medico.trim().isEmpty()) {
            consulta.setMedico(medico);
        }
        
        System.out.print("Novas observações (ou Enter para manter): ");
        String observacoes = scanner.nextLine();
        if (!observacoes.trim().isEmpty()) {
            consulta.setObservacoes(observacoes);
        }
        
        if (consultaDAO.atualizar(consulta)) {
            System.out.println("Consulta atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar consulta.");
        }
    }

    private static void cancelarConsulta() {
        System.out.println("\n=== CANCELAR CONSULTA ===");
        System.out.print("Digite o ID da consulta a ser cancelada: ");
        int id = obterOpcao();
        
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        
        Cliente cliente = clienteDAO.buscarPorId(consulta.getClientId());
        System.out.println("Consulta - Cliente: " + (cliente != null ? cliente.getNome() : "N/A"));
        System.out.println("Data/Hora: " + consulta.getData() + " " + consulta.getHorario());
        System.out.print("Confirma cancelamento? (s/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.toLowerCase().startsWith("s")) {
            if (consultaDAO.excluir(id)) {
                System.out.println("Consulta cancelada com sucesso!");
            } else {
                System.out.println("Erro ao cancelar consulta.");
            }
        } else {
            System.out.println("Cancelamento cancelado.");
        }
    }

    private static int obterOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.next(); // Consumir a entrada inválida
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        return opcao;
    }
}

