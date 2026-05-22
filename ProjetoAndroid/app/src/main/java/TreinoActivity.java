package com.example.b1academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b1academia.adapter.TreinoAdapter;
import com.example.b1academia.api.ApiClient;
import com.example.b1academia.api.ApiService;
import com.example.b1academia.model.Treino;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreinoActivity extends AppCompatActivity implements TreinoAdapter.OnExcluirClickListener {

    private EditText edtNomeTreino;
    private EditText edtDescricaoTreino;
    private Button btnSalvarTreino;
    private ListView listaTreinos;

    private ApiService apiService;
    private TreinoAdapter treinoAdapter;
    private List<Treino> treinos;

    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        edtNomeTreino = findViewById(R.id.edtNomeTreino);
        edtDescricaoTreino = findViewById(R.id.edtDescricaoTreino);
        btnSalvarTreino = findViewById(R.id.btnSalvarTreino);
        listaTreinos = findViewById(R.id.listaTreinos);

        apiService = ApiClient.getClient().create(ApiService.class);

        SharedPreferences preferences = getSharedPreferences("usuario_logado", MODE_PRIVATE);
        usuarioId = preferences.getInt("usuario_id", 0);

        carregarTreinos();

        btnSalvarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarTreino();
            }
        });
    }

    private void salvarTreino() {
        String nome = edtNomeTreino.getText().toString().trim();
        String descricao = edtDescricaoTreino.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Digite o nome do treino", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioId == 0) {
            Toast.makeText(this, "Usuário não identificado. Faça login novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        Treino treino = new Treino(nome, descricao, usuarioId);

        apiService.criarTreino(treino).enqueue(new Callback<Treino>() {
            @Override
            public void onResponse(Call<Treino> call, Response<Treino> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TreinoActivity.this, "Treino salvo com sucesso", Toast.LENGTH_SHORT).show();

                    edtNomeTreino.setText("");
                    edtDescricaoTreino.setText("");

                    carregarTreinos();
                } else {
                    Toast.makeText(TreinoActivity.this, "Erro ao salvar treino", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Treino> call, Throwable t) {
                Toast.makeText(TreinoActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarTreinos() {
        apiService.listarTreinos().enqueue(new Callback<List<Treino>>() {
            @Override
            public void onResponse(Call<List<Treino>> call, Response<List<Treino>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    treinos = response.body();
                    treinoAdapter = new TreinoAdapter(TreinoActivity.this, treinos, TreinoActivity.this);
                    listaTreinos.setAdapter(treinoAdapter);
                } else {
                    Toast.makeText(TreinoActivity.this, "Erro ao carregar treinos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Treino>> call, Throwable t) {
                Toast.makeText(TreinoActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onExcluirClick(final Treino treino) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir treino")
                .setMessage("Deseja excluir \"" + treino.getNome() + "\"?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apiService.excluirTreino(treino.getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(TreinoActivity.this, "Treino excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    carregarTreinos();
                                } else {
                                    Toast.makeText(TreinoActivity.this, "Erro ao excluir. Verifique se há execuções vinculadas.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(TreinoActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}