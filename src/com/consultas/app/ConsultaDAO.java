package com.consultas.app;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    /* ──────────────────── VERIFICA CONFLITO ──────────────────── */
    /** Retorna true se já existir consulta no mesmo dia + horário para o médico. */
    public boolean horarioOcupado(LocalDate data, LocalTime horario, String medico) {
        String sql = "SELECT COUNT(*) FROM consulta "
                   + "WHERE data = ? AND horario = ? AND LOWER(medico) = LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.toString());
            stmt.setString(2, horario.toString());
            stmt.setString(3, medico);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao verificar horário ocupado: " + e.getMessage());
            return true;               // em caso de erro, considera ocupado
        }
    }

    /* ─────────────────────  INSERIR CONSULTA  ──────────────────── */
    public boolean inserir(Consulta consulta) {
        if (horarioOcupado(consulta.getData(), consulta.getHorario(), consulta.getMedico())) {
            System.err.println("Horário já ocupado para este médico.");
            return false;
        }

        String sql = "INSERT INTO consulta "
                   + "(client_id, data, horario, medico, observacoes) "
                   + "VALUES (?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt   (1, consulta.getClientId());
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

    /* ───────────  RESTANTE DOS MÉTODOS JÁ EXISTENTES  ─────────── */
    public List<Consulta> listarTodas() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta ORDER BY data, horario";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                consultas.add(new Consulta(
                    rs.getInt   ("consulta_id"),
                    rs.getInt   ("client_id"),
                    LocalDate.parse(rs.getString("data")),
                    LocalTime.parse(rs.getString("horario")),
                    rs.getString("medico"),
                    rs.getString("observacoes")
                ));
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
                    rs.getInt   ("consulta_id"),
                    rs.getInt   ("client_id"),
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

    public boolean atualizar(Consulta c) {
        if (horarioOcupado(c.getData(), c.getHorario(), c.getMedico())) {
            System.err.println("Horário já ocupado para este médico.");
            return false;
        }
        String sql = "UPDATE consulta SET client_id=?, data=?, horario=?, medico=?, observacoes=? "
                   + "WHERE consulta_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt   (1, c.getClientId());
            stmt.setString(2, c.getData().toString());
            stmt.setString(3, c.getHorario().toString());
            stmt.setString(4, c.getMedico());
            stmt.setString(5, c.getObservacoes());
            stmt.setInt   (6, c.getConsultaId());
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

    /* Busca por cliente */
    public List<Consulta> buscarPorClienteId(int clienteId) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE client_id=? ORDER BY data, horario";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                consultas.add(new Consulta(
                    rs.getInt   ("consulta_id"),
                    rs.getInt   ("client_id"),
                    LocalDate.parse(rs.getString("data")),
                    LocalTime.parse(rs.getString("horario")),
                    rs.getString("medico"),
                    rs.getString("observacoes")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consultas: " + e.getMessage());
        }
        return consultas;
    }
}
