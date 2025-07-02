package com.consultas.app;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseInitializer {

    public static void initializeDatabase(String schemaFilePath) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Read SQL schema from file
            StringBuilder schemaSql = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(schemaFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    schemaSql.append(line).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo de schema SQL: " + e.getMessage());
                return;
            }

            // Execute SQL statements
            String[] statements = schemaSql.toString().split(";");
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement.trim());
                }
            }
            System.out.println("Banco de dados inicializado com sucesso!");

            // Insert default admin user if not exists
            if (!new LoginDAO().autenticar("admin", "admin")) {
                new LoginDAO().criarUsuario("admin", "admin");
                System.out.println("Usuário \'admin\' criado com sucesso.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Caminho para o arquivo schema.sql
        String schemaFilePath = "/home/ubuntu/upload/schema.sql"; // Ajuste o caminho conforme necessário
        initializeDatabase(schemaFilePath);
    }
}


