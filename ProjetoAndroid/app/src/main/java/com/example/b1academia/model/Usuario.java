package com.example.b1academia.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private Double peso;
    private Double altura;
    private String meta;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, Double peso, Double altura, String meta) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.peso = peso;
        this.altura = altura;
        this.meta = meta;
    }

    public Usuario(int id, String nome, String email, Double peso, Double altura, String meta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.peso = peso;
        this.altura = altura;
        this.meta = meta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }


    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }


    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}