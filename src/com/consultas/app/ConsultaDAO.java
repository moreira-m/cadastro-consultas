package com.consultas.app;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    
    public boolean inserir(Consulta consulta) {
        String sql = "INSERT INTO consulta (client_id, data, horario, medico, observacoes) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, consulta.getClientId());
            stmt.setString(2, consulta.getData().toString());
            stmt.setString(3, consulta.getHorario().toString());
            stmt.setString(4, consulta.getMedico());
            stmt.setString(5, consulta.getObservacoes());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir consulta: " + e.getMessage());
            return false;
        }
    }
    
    public List<Consulta> listarTodas() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta ORDER BY data, horario";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Consulta consulta = new Consulta(
                    rs.getInt("consulta_id"),
                    rs.getInt("client_id"),
                    LocalDate.parse(rs.getString("data")),
                    LocalTime.parse(rs.getString("horario")),
                    rs.getString("medico"),
                    rs.getString("observacoes")
                );
                consultas.add(consulta);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar consultas: " + e.getMessage());
        }
        
        return consultas;
    }
    
    public Consulta buscarPorId(int id) {
        String sql = "SELECT * FROM consulta WHERE consulta_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Consulta(
                    rs.getInt("consulta_id"),
                    rs.getInt("client_id"),
                    LocalDate.parse(rs.getString("data")),
                    LocalTime.parse(rs.getString("horario")),
                    rs.getString("medico"),
                    rs.getString("observacoes")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consulta: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean atualizar(Consulta consulta) {
        String sql = "UPDATE consulta SET client_id = ?, data = ?, horario = ?, medico = ?, observacoes = ? WHERE consulta_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, consulta.getClientId());
            stmt.setString(2, consulta.getData().toString());
            stmt.setString(3, consulta.getHorario().toString());
            stmt.setString(4, consulta.getMedico());
            stmt.setString(5, consulta.getObservacoes());
            stmt.setInt(6, consulta.getConsultaId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar consulta: " + e.getMessage());
            return false;
        }
    }
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM consulta WHERE consulta_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir consulta: " + e.getMessage());
            return false;
        }
    }

    public List<Consulta> buscarPorClienteId(int clienteId) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE client_id = ? ORDER BY data, horario";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta(
                    rs.getInt("consulta_id"),
                    rs.getInt("client_id"),
                    LocalDate.parse(rs.getString("data")),
                    LocalTime.parse(rs.getString("horario")),
                    rs.getString("medico"),
                    rs.getString("observacoes")
                );
                consultas.add(consulta);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar consultas por cliente ID: " + e.getMessage());
        }

        return consultas;
    }
}
