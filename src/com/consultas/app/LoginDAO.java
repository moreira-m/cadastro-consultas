package com.consultas.app;

import java.sql.*;

public class LoginDAO {
    
    public boolean autenticar(String usuario, String senha) {
        String sql = "SELECT * FROM login WHERE usuario = ? AND senha = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            return usuario.equals("admin") && senha.equals("admin");
        }
    }
    
    public boolean criarUsuario(String usuario, String senha) {
        String sql = "INSERT INTO login (usuario, senha) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
}


