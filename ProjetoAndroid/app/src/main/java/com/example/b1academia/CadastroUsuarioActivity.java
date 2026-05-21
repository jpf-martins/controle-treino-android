package com.example.b1academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b1academia.api.ApiClient;
import com.example.b1academia.api.ApiService;
import com.example.b1academia.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText edtNomeCadastro;
    private EditText edtEmailCadastro;
    private EditText edtSenhaCadastro;
    private EditText edtPesoCadastro;
    private EditText edtAlturaCadastro;
    private EditText edtMetaCadastro;
    private Button btnCadastrarUsuario;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtNomeCadastro = findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro);
        edtPesoCadastro = findViewById(R.id.edtPesoCadastro);
        edtAlturaCadastro = findViewById(R.id.edtAlturaCadastro);
        edtMetaCadastro = findViewById(R.id.edtMetaCadastro);
        btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        String nome = edtNomeCadastro.getText().toString().trim();
        String email = edtEmailCadastro.getText().toString().trim();
        String senha = edtSenhaCadastro.getText().toString().trim();
        String pesoTexto = edtPesoCadastro.getText().toString().trim();
        String alturaTexto = edtAlturaCadastro.getText().toString().trim();
        String meta = edtMetaCadastro.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha nome, e-mail e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        Double peso = pesoTexto.isEmpty() ? null : Double.parseDouble(pesoTexto);
        Double altura = alturaTexto.isEmpty() ? null : Double.parseDouble(alturaTexto);

        Usuario usuario = new Usuario(nome, email, senha, peso, altura, meta);

        apiService.criarUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar. Verifique os dados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(CadastroUsuarioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}