package com.example.b1academia.model;

import com.google.gson.annotations.SerializedName;

public class Exercicio {

    private int id;
    private String nome;
    private int series;
    private int repeticoes;

    @SerializedName("grupo_muscular_id")
    private int grupoId;

    public Exercicio() {
    }

    public Exercicio(String nome, int series, int repeticoes, int grupoId) {
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.grupoId = grupoId;
    }

    public Exercicio(int id, String nome, int series, int repeticoes, int grupoId) {
        this.id = id;
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.grupoId = grupoId;
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


    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }


    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }


    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }
}