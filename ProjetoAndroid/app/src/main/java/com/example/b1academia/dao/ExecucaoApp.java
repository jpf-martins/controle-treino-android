package com.example.b1academia.model;

import com.google.gson.annotations.SerializedName;

public class ExecucaoApp {

    private int id;

    @SerializedName("data_execucao")
    private String dataExecucao;

    private double carga;
    private String observacao;

    @SerializedName("exercicio_id")
    private int exercicioId;

    @SerializedName("treino_id")
    private int treinoId;

    public ExecucaoApp() {
    }

    public ExecucaoApp(String dataExecucao, double carga, String observacao, int exercicioId, int treinoId) {
        this.dataExecucao = dataExecucao;
        this.carga = carga;
        this.observacao = observacao;
        this.exercicioId = exercicioId;
        this.treinoId = treinoId;
    }

    public ExecucaoApp(int id, String dataExecucao, double carga, String observacao, int exercicioId, int treinoId) {
        this.id = id;
        this.dataExecucao = dataExecucao;
        this.carga = carga;
        this.observacao = observacao;
        this.exercicioId = exercicioId;
        this.treinoId = treinoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(String dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public String getData() {
        return dataExecucao;
    }

    public void setData(String data) {
        this.dataExecucao = data;
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


    public int getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(int treinoId) {
        this.treinoId = treinoId;
    }
}