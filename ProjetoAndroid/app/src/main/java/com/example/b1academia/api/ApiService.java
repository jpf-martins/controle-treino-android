package com.example.b1academia.api;
import com.example.b1academia.model.LoginRequest;
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
}