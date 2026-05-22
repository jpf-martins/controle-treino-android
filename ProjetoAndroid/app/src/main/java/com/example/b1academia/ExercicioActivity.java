package com.example.b1academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.b1academia.adapter.ExercicioAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.b1academia.api.ApiClient;
import com.example.b1academia.api.ApiService;
import com.example.b1academia.model.Exercicio;
import com.example.b1academia.model.GrupoMuscular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercicioActivity extends AppCompatActivity implements ExercicioAdapter.OnExcluirClickListener {

    private EditText edtNomeExercicio;
    private EditText edtSeries;
    private EditText edtRepeticoes;
    private Spinner spinnerGrupos;
    private Button btnSalvarExercicio;
    private ListView listaExercicios;

    private ApiService apiService;

    private List<GrupoMuscular> grupos;
    private List<String> nomesGrupos;
    private List<Exercicio> exercicios;

    private ArrayAdapter<String> spinnerAdapter;
    private ExercicioAdapter exercicioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicio);

        edtNomeExercicio = findViewById(R.id.edtNomeExercicio);
        edtSeries = findViewById(R.id.edtSeries);
        edtRepeticoes = findViewById(R.id.edtRepeticoes);
        spinnerGrupos = findViewById(R.id.spinnerGrupos);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);
        listaExercicios = findViewById(R.id.listaExercicios);

        apiService = ApiClient.getClient().create(ApiService.class);

        carregarSpinnerGrupos();
        carregarListaExercicios();

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarExercicio();
            }
        });
    }

    private void salvarExercicio() {
        String nome = edtNomeExercicio.getText().toString().trim();
        String seriesTexto = edtSeries.getText().toString().trim();
        String repeticoesTexto = edtRepeticoes.getText().toString().trim();

        if (nome.isEmpty() || seriesTexto.isEmpty() || repeticoesTexto.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (grupos == null || grupos.isEmpty()) {
            Toast.makeText(this, "Cadastre um grupo muscular primeiro", Toast.LENGTH_SHORT).show();
            return;
        }

        int series = Integer.parseInt(seriesTexto);
        int repeticoes = Integer.parseInt(repeticoesTexto);
        int posicaoGrupo = spinnerGrupos.getSelectedItemPosition();
        int grupoId = grupos.get(posicaoGrupo).getId();

        Exercicio exercicio = new Exercicio(nome, series, repeticoes, grupoId);

        apiService.criarExercicio(exercicio).enqueue(new Callback<Exercicio>() {
            @Override
            public void onResponse(Call<Exercicio> call, Response<Exercicio> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ExercicioActivity.this, "Exercício salvo com sucesso", Toast.LENGTH_SHORT).show();

                    edtNomeExercicio.setText("");
                    edtSeries.setText("");
                    edtRepeticoes.setText("");

                    carregarListaExercicios();
                } else {
                    Toast.makeText(ExercicioActivity.this, "Erro ao salvar exercício", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Exercicio> call, Throwable t) {
                Toast.makeText(ExercicioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarSpinnerGrupos() {
        apiService.listarGrupos().enqueue(new Callback<List<GrupoMuscular>>() {
            @Override
            public void onResponse(Call<List<GrupoMuscular>> call, Response<List<GrupoMuscular>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    grupos = response.body();
                    nomesGrupos = new ArrayList<>();

                    for (GrupoMuscular grupo : grupos) {
                        nomesGrupos.add(grupo.getNome());
                    }

                    spinnerAdapter = new ArrayAdapter<>(ExercicioActivity.this, R.layout.spinner_item, nomesGrupos);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    spinnerGrupos.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<GrupoMuscular>> call, Throwable t) {
                Toast.makeText(ExercicioActivity.this, "Falha ao carregar grupos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarListaExercicios() {
        apiService.listarExercicios().enqueue(new Callback<List<Exercicio>>() {
            @Override
            public void onResponse(Call<List<Exercicio>> call, Response<List<Exercicio>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exercicios = response.body();

                    Map<Integer, String> mapaGrupos = new HashMap<>();

                    if (grupos != null) {
                        for (GrupoMuscular grupo : grupos) {
                            mapaGrupos.put(grupo.getId(), grupo.getNome());
                        }
                    }

                    exercicioAdapter = new ExercicioAdapter(ExercicioActivity.this, exercicios, mapaGrupos, ExercicioActivity.this);
                    listaExercicios.setAdapter(exercicioAdapter);
                } else {
                    Toast.makeText(ExercicioActivity.this, "Erro ao carregar exercícios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Exercicio>> call, Throwable t) {
                Toast.makeText(ExercicioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onExcluirClick(final Exercicio exercicio) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir exercício")
                .setMessage("Deseja excluir \"" + exercicio.getNome() + "\"?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apiService.excluirExercicio(exercicio.getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ExercicioActivity.this, "Exercício excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    carregarListaExercicios();
                                } else {
                                    Toast.makeText(ExercicioActivity.this, "Erro ao excluir. Verifique se há execuções vinculadas.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ExercicioActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}