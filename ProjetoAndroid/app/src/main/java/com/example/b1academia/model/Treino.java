package com.example.b1academia.model;

import com.google.gson.annotations.SerializedName;

public class Treino {

    private int id;
    private String nome;
    private String descricao;

    @SerializedName("usuario_id")
    private int usuarioId;

    public Treino() {
    }

    public Treino(String nome, String descricao, int usuarioId) {
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioId = usuarioId;
    }

    public Treino(int id, String nome, String descricao, int usuarioId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioId = usuarioId;
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


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}