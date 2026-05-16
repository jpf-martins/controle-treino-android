package com.example.b1academia.model;

public class ExecucaoTreino {

    private int id;
    private String data;
    private double carga;
    private String observacao;
    private int exercicioId;

    public ExecucaoTreino() {
    }

    public ExecucaoTreino(int id, String data, double carga, String observacao, int exercicioId) {
        this.id = id;
        this.data = data;
        this.carga = carga;
        this.observacao = observacao;
        this.exercicioId = exercicioId;
    }

    public ExecucaoTreino(String data, double carga, String observacao, int exercicioId) {
        this.data = data;
        this.carga = carga;
        this.observacao = observacao;
        this.exercicioId = exercicioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getCarga() {
        return carga;
    }

    public void setCarga(double carga) {
        this.carga = carga;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getExercicioId() {
        return exercicioId;
    }

    public void setExercicioId(int exercicioId) {
        this.exercicioId = exercicioId;
    }
}