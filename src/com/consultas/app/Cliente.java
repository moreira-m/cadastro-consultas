package com.consultas.app;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private int cpf;
    
    // Construtor padrão
    public Cliente() {}
    
    // Construtor com parâmetros
    public Cliente(int id, String nome, String telefone, String email, int cpf) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public int getCPF(){
        return cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(int cpf){
        this.cpf = cpf;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome=\"" + nome + "\"" +
                ", telefone=\"" + telefone + "\"" +
                ", email=\"" + email + "\"" +
                ", cpf=\"" + cpf + "\""+
                "}";
    }
}

