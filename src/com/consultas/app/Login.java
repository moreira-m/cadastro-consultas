package com.consultas.app;

public class Login {
    private int userId;
    private String usuario;
    private String senha;

    public Login(int userId, String usuario, String senha) {
        this.userId = userId;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Login(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}


