package com.example.b1academia.model;

public class Usuario {

    private int id;
    private String nome;
    private Double peso;
    private Double altura;
    private String meta;

    public Usuario() {
    }

    public Usuario(String nome, Double peso, Double altura, String meta) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.meta = meta;
    }

    public Usuario(int id, String nome, Double peso, Double altura, String meta) {
        this.id = id;
        this.nome = nome;
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