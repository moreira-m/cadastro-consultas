package com.consultas.app;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int consultaId;
    private int clientId;
    private LocalDate data;
    private LocalTime horario;
    private String medico;
    private String observacoes;

    public Consulta(int consultaId, int clientId, LocalDate data, LocalTime horario, String medico, String observacoes) {
        this.consultaId = consultaId;
        this.clientId = clientId;
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.observacoes = observacoes;
    }

    public Consulta(int clientId, LocalDate data, LocalTime horario, String medico, String observacoes) {
        this.clientId = clientId;
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.observacoes = observacoes;
    }

    // Getters
    public int getConsultaId() {
        return consultaId;
    }

    public int getClientId() {
        return clientId;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public String getMedico() {
        return medico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    // Setters
    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}


