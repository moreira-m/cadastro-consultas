package com.consultas.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configurações do banco de dados SQLite
    private static final String URL = "jdbc:sqlite:consultas.db";
    
    // Método para obter conexão
    public static Connection getConnection() throws SQLException {
        try {
            // Carrega o driver do SQLite
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do SQLite não encontrado", e);
        }
    }
    
    // Método para testar a conexão
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Erro ao testar conexão: " + e.getMessage());
            return false;
        }
    }
}


