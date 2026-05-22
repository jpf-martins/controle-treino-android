package com.example.b1academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.b1academia.adapter.GrupoMuscularAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b1academia.api.ApiClient;
import com.example.b1academia.api.ApiService;
import com.example.b1academia.model.GrupoMuscular;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrupoMuscularActivity extends AppCompatActivity implements GrupoMuscularAdapter.OnExcluirClickListener {

    private EditText edtNomeGrupo;
    private Button btnSalvarGrupo;
    private ListView listaGrupos;

    private ApiService apiService;
    private GrupoMuscularAdapter adapter;
    private List<GrupoMuscular> grupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_muscular);

        edtNomeGrupo = findViewById(R.id.edtNomeGrupo);
        btnSalvarGrupo = findViewById(R.id.btnSalvarGrupo);
        listaGrupos = findViewById(R.id.listaGrupos);

        apiService = ApiClient.getClient().create(ApiService.class);

        carregarLista();

        btnSalvarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarGrupo();
            }
        });
    }

    private void salvarGrupo() {
        String nome = edtNomeGrupo.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Digite o nome do grupo", Toast.LENGTH_SHORT).show();
            return;
        }

        GrupoMuscular grupo = new GrupoMuscular(nome);

        apiService.criarGrupo(grupo).enqueue(new Callback<GrupoMuscular>() {
            @Override
            public void onResponse(Call<GrupoMuscular> call, Response<GrupoMuscular> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GrupoMuscularActivity.this, "Grupo salvo com sucesso", Toast.LENGTH_SHORT).show();
                    edtNomeGrupo.setText("");
                    carregarLista();
                } else {
                    Toast.makeText(GrupoMuscularActivity.this, "Erro ao salvar grupo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GrupoMuscular> call, Throwable t) {
                Toast.makeText(GrupoMuscularActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarLista() {
        apiService.listarGrupos().enqueue(new Callback<List<GrupoMuscular>>() {
            @Override
            public void onResponse(Call<List<GrupoMuscular>> call, Response<List<GrupoMuscular>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    grupos = response.body();
                    adapter = new GrupoMuscularAdapter(GrupoMuscularActivity.this, grupos, GrupoMuscularActivity.this);
                    listaGrupos.setAdapter(adapter);
                } else {
                    Toast.makeText(GrupoMuscularActivity.this, "Erro ao carregar grupos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GrupoMuscular>> call, Throwable t) {
                Toast.makeText(GrupoMuscularActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onExcluirClick(final GrupoMuscular grupo) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir grupo")
                .setMessage("Deseja excluir \"" + grupo.getNome() + "\"?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apiService.excluirGrupo(grupo.getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(GrupoMuscularActivity.this, "Grupo excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    carregarLista();
                                } else {
                                    Toast.makeText(GrupoMuscularActivity.this, "Erro ao excluir. Verifique se há exercícios vinculados.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(GrupoMuscularActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}