package com.example.b1academia.api;

import com.example.b1academia.model.ExecucaoApp;
import com.example.b1academia.model.Exercicio;
import com.example.b1academia.model.GrupoMuscular;
import com.example.b1academia.model.LoginRequest;
import com.example.b1academia.model.Treino;
import com.example.b1academia.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("usuarios/")
    Call<Usuario> criarUsuario(@Body Usuario usuario);

    @POST("usuarios/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @GET("usuarios/")
    Call<List<Usuario>> listarUsuarios();

    @GET("usuarios/{id}")
    Call<Usuario> buscarUsuario(@Path("id") int id);

    @PUT("usuarios/{id}")
    Call<Usuario> atualizarUsuario(@Path("id") int id, @Body Usuario usuario);

    @DELETE("usuarios/{id}")
    Call<Void> excluirUsuario(@Path("id") int id);


    @POST("grupos/")
    Call<GrupoMuscular> criarGrupo(@Body GrupoMuscular grupo);

    @GET("grupos/")
    Call<List<GrupoMuscular>> listarGrupos();

    @GET("grupos/{id}")
    Call<GrupoMuscular> buscarGrupo(@Path("id") int id);

    @PUT("grupos/{id}")
    Call<GrupoMuscular> atualizarGrupo(@Path("id") int id, @Body GrupoMuscular grupo);

    @DELETE("grupos/{id}")
    Call<Void> excluirGrupo(@Path("id") int id);


    @POST("exercicios/")
    Call<Exercicio> criarExercicio(@Body Exercicio exercicio);

    @GET("exercicios/")
    Call<List<Exercicio>> listarExercicios();

    @GET("exercicios/{id}")
    Call<Exercicio> buscarExercicio(@Path("id") int id);

    @PUT("exercicios/{id}")
    Call<Exercicio> atualizarExercicio(@Path("id") int id, @Body Exercicio exercicio);

    @DELETE("exercicios/{id}")
    Call<Void> excluirExercicio(@Path("id") int id);


    @POST("treinos/")
    Call<Treino> criarTreino(@Body Treino treino);

    @GET("treinos/")
    Call<List<Treino>> listarTreinos();

    @GET("treinos/{id}")
    Call<Treino> buscarTreino(@Path("id") int id);

    @PUT("treinos/{id}")
    Call<Treino> atualizarTreino(@Path("id") int id, @Body Treino treino);

    @DELETE("treinos/{id}")
    Call<Void> excluirTreino(@Path("id") int id);


    @POST("execucoes/")
    Call<ExecucaoApp> criarExecucao(@Body ExecucaoApp execucao);

    @GET("execucoes/")
    Call<List<ExecucaoApp>> listarExecucoes();

    @GET("execucoes/{id}")
    Call<ExecucaoApp> buscarExecucao(@Path("id") int id);

    @PUT("execucoes/{id}")
    Call<ExecucaoApp> atualizarExecucao(@Path("id") int id, @Body ExecucaoApp execucao);

    @DELETE("execucoes/{id}")
    Call<Void> excluirExecucao(@Path("id") int id);
}