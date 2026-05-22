package com.example.b1academia;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b1academia.adapter.ExecucaoAdapter;
import com.example.b1academia.api.ApiClient;
import com.example.b1academia.api.ApiService;
import com.example.b1academia.model.ExecucaoApp;
import com.example.b1academia.model.Exercicio;
import com.example.b1academia.model.Treino;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExecucaoActivity extends AppCompatActivity implements ExecucaoAdapter.OnExcluirClickListener {

    private EditText edtDataExecucao;
    private EditText edtCargaExecucao;
    private EditText edtObservacaoExecucao;
    private Spinner spinnerExercicios;
    private Spinner spinnerTreinos;
    private Button btnSalvarExecucao;
    private ListView listaExecucoes;

    private ApiService apiService;

    private List<Exercicio> exercicios;
    private List<Treino> treinos;
    private List<String> nomesExercicios;
    private List<String> nomesTreinos;
    private List<ExecucaoApp> execucoes;

    private ArrayAdapter<String> spinnerExercicioAdapter;
    private ArrayAdapter<String> spinnerTreinoAdapter;
    private ExecucaoAdapter execucaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execucao);

        edtDataExecucao = findViewById(R.id.edtDataExecucao);
        edtCargaExecucao = findViewById(R.id.edtCargaExecucao);
        edtObservacaoExecucao = findViewById(R.id.edtObservacaoExecucao);
        spinnerExercicios = findViewById(R.id.spinnerExercicios);
        spinnerTreinos = findViewById(R.id.spinnerTreinos);
        btnSalvarExecucao = findViewById(R.id.btnSalvarExecucao);
        listaExecucoes = findViewById(R.id.listaExecucoes);

        apiService = ApiClient.getClient().create(ApiService.class);

        edtDataExecucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirSeletorData();
            }
        });

        carregarSpinnerExercicios();
        carregarSpinnerTreinos();
        carregarListaExecucoes();

        btnSalvarExecucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarExecucao();
            }
        });
    }

    private void abrirSeletorData() {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, anoSelecionado, mesSelecionado, diaSelecionado) -> {
                    Calendar dataSelecionada = Calendar.getInstance();
                    dataSelecionada.set(anoSelecionado, mesSelecionado, diaSelecionado);

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    edtDataExecucao.setText(formato.format(dataSelecionada.getTime()));
                },
                ano,
                mes,
                dia
        );

        datePickerDialog.show();
    }

    private void salvarExecucao() {
        String data = edtDataExecucao.getText().toString().trim();
        String cargaTexto = edtCargaExecucao.getText().toString().trim();
        String observacao = edtObservacaoExecucao.getText().toString().trim();

        if (data.isEmpty() || cargaTexto.isEmpty()) {
            Toast.makeText(this, "Preencha data e carga", Toast.LENGTH_SHORT).show();
            return;
        }

        if (exercicios == null || exercicios.isEmpty()) {
            Toast.makeText(this, "Cadastre um exercício primeiro", Toast.LENGTH_SHORT).show();
            return;
        }

        if (treinos == null || treinos.isEmpty()) {
            Toast.makeText(this, "Cadastre um treino primeiro", Toast.LENGTH_SHORT).show();
            return;
        }

        double carga = Double.parseDouble(cargaTexto);

        int posicaoExercicio = spinnerExercicios.getSelectedItemPosition();
        int exercicioId = exercicios.get(posicaoExercicio).getId();

        int posicaoTreino = spinnerTreinos.getSelectedItemPosition();
        int treinoId = treinos.get(posicaoTreino).getId();

        ExecucaoApp execucao = new ExecucaoApp(data, carga, observacao, exercicioId, treinoId);

        apiService.criarExecucao(execucao).enqueue(new Callback<ExecucaoApp>() {
            @Override
            public void onResponse(Call<ExecucaoApp> call, Response<ExecucaoApp> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ExecucaoActivity.this, "Execução salva com sucesso", Toast.LENGTH_SHORT).show();

                    edtDataExecucao.setText("");
                    edtCargaExecucao.setText("");
                    edtObservacaoExecucao.setText("");

                    carregarListaExecucoes();
                } else {
                    Toast.makeText(ExecucaoActivity.this, "Erro ao salvar execução", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ExecucaoApp> call, Throwable t) {
                Toast.makeText(ExecucaoActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarSpinnerExercicios() {
        apiService.listarExercicios().enqueue(new Callback<List<Exercicio>>() {
            @Override
            public void onResponse(Call<List<Exercicio>> call, Response<List<Exercicio>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exercicios = response.body();
                    nomesExercicios = new ArrayList<>();

                    for (Exercicio exercicio : exercicios) {
                        nomesExercicios.add(exercicio.getNome());
                    }

                    spinnerExercicioAdapter = new ArrayAdapter<>(ExecucaoActivity.this, R.layout.spinner_item, nomesExercicios);
                    spinnerExercicioAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    spinnerExercicios.setAdapter(spinnerExercicioAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Exercicio>> call, Throwable t) {
                Toast.makeText(ExecucaoActivity.this, "Falha ao carregar exercícios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarSpinnerTreinos() {
        apiService.listarTreinos().enqueue(new Callback<List<Treino>>() {
            @Override
            public void onResponse(Call<List<Treino>> call, Response<List<Treino>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    treinos = response.body();
                    nomesTreinos = new ArrayList<>();

                    for (Treino treino : treinos) {
                        nomesTreinos.add(treino.getNome());
                    }

                    spinnerTreinoAdapter = new ArrayAdapter<>(ExecucaoActivity.this, R.layout.spinner_item, nomesTreinos);
                    spinnerTreinoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    spinnerTreinos.setAdapter(spinnerTreinoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Treino>> call, Throwable t) {
                Toast.makeText(ExecucaoActivity.this, "Falha ao carregar treinos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarListaExecucoes() {
        apiService.listarExecucoes().enqueue(new Callback<List<ExecucaoApp>>() {
            @Override
            public void onResponse(Call<List<ExecucaoApp>> call, Response<List<ExecucaoApp>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    execucoes = response.body();

                    Map<Integer, String> mapaExercicios = new HashMap<>();
                    Map<Integer, String> mapaTreinos = new HashMap<>();

                    if (exercicios != null) {
                        for (Exercicio exercicio : exercicios) {
                            mapaExercicios.put(exercicio.getId(), exercicio.getNome());
                        }
                    }

                    if (treinos != null) {
                        for (Treino treino : treinos) {
                            mapaTreinos.put(treino.getId(), treino.getNome());
                        }
                    }

                    execucaoAdapter = new ExecucaoAdapter(ExecucaoActivity.this, execucoes, mapaExercicios, mapaTreinos, ExecucaoActivity.this);
                    listaExecucoes.setAdapter(execucaoAdapter);
                } else {
                    Toast.makeText(ExecucaoActivity.this, "Erro ao carregar execuções", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ExecucaoApp>> call, Throwable t) {
                Toast.makeText(ExecucaoActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onExcluirClick(final ExecucaoApp execucao) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir execução")
                .setMessage("Deseja excluir esta execução?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apiService.excluirExecucao(execucao.getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ExecucaoActivity.this, "Execução excluída com sucesso", Toast.LENGTH_SHORT).show();
                                    carregarListaExecucoes();
                                } else {
                                    Toast.makeText(ExecucaoActivity.this, "Erro ao excluir execução", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ExecucaoActivity.this, "Falha na conexão com a API", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}