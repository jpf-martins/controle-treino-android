package com.example.b1academia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b1academia.adapter.UsuarioAdapter;
import com.example.b1academia.api.ApiClient;
import com.example.b1academia.api.ApiService;
import com.example.b1academia.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity implements UsuarioAdapter.OnExcluirClickListener {

    private EditText edtNomeUsuario;
    private EditText edtPesoUsuario;
    private EditText edtAlturaUsuario;
    private EditText edtMetaUsuario;
    private Button btnSalvarUsuario;
    private ListView listaUsuarios;

    private ApiService apiService;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        edtNomeUsuario = findViewById(R.id.edtNomeUsuario);
        edtPesoUsuario = findViewById(R.id.edtPesoUsuario);
        edtAlturaUsuario = findViewById(R.id.edtAlturaUsuario);
        edtMetaUsuario = findViewById(R.id.edtMetaUsuario);
        btnSalvarUsuario = findViewById(R.id.btnSalvarUsuario);
        listaUsuarios = findViewById(R.id.listaUsuarios);

        apiService = ApiClient.getClient().create(ApiService.class);

        carregarUsuarios();

        btnSalvarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarUsuario();
            }
        });
    }

    private void salvarUsuario() {
        String nome = edtNomeUsuario.getText().toString().trim();
        String pesoTexto = edtPesoUsuario.getText().toString().trim();
        String alturaTexto = edtAlturaUsuario.getText().toString().trim();
        String meta = edtMetaUsuario.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Digite o nome do usuário", Toast.LENGTH_SHORT).show();
            return;
        }

        Double peso = pesoTexto.isEmpty() ? null : Double.parseDouble(pesoTexto);
        Double altura = alturaTexto.isEmpty() ? null : Double.parseDouble(alturaTexto);

        Usuario usuario = new Usuario(nome, "", "", peso, altura, meta);

        apiService.criarUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UsuarioActivity.this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();

                    edtNomeUsuario.setText("");
                    edtPesoUsuario.setText("");
                    edtAlturaUsuario.setText("");
                    edtMetaUsuario.setText("");

                    carregarUsuarios();
                } else {
                    Toast.makeText(UsuarioActivity.this, "Erro ao salvar usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(UsuarioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarUsuarios() {
        apiService.listarUsuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarios = response.body();
                    usuarioAdapter = new UsuarioAdapter(UsuarioActivity.this, usuarios, UsuarioActivity.this);
                    listaUsuarios.setAdapter(usuarioAdapter);
                } else {
                    Toast.makeText(UsuarioActivity.this, "Erro ao carregar usuários", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(UsuarioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onExcluirClick(Usuario usuario) {
        apiService.excluirUsuario(usuario.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UsuarioActivity.this, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
                    carregarUsuarios();
                } else {
                    Toast.makeText(UsuarioActivity.this, "Erro ao excluir usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UsuarioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}