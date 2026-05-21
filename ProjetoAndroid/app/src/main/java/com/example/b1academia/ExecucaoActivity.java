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
import com.example.b1academia.dao.ExecucaoAppDAO;
import com.example.b1academia.dao.ExercicioDAO;
import com.example.b1academia.model.ExecucaoApp;
import com.example.b1academia.model.Exercicio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExecucaoActivity extends AppCompatActivity implements ExecucaoAdapter.OnExcluirClickListener {

    private EditText edtDataExecucao;
    private EditText edtCargaExecucao;
    private EditText edtObservacaoExecucao;
    private Spinner spinnerExercicios;
    private Button btnSalvarExecucao;
    private ListView listaExecucoes;

    private ExecucaoAppDAO execucaoAppDAO;
    private ExercicioDAO exercicioDAO;

    private List<Exercicio> exercicios;
    private List<String> nomesExercicios;
    private List<ExecucaoApp> execucoes;

    private ArrayAdapter<String> spinnerAdapter;
    private ExecucaoAdapter execucaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execucao);

        edtDataExecucao = findViewById(R.id.edtDataExecucao);
        edtCargaExecucao = findViewById(R.id.edtCargaExecucao);
        edtObservacaoExecucao = findViewById(R.id.edtObservacaoExecucao);
        spinnerExercicios = findViewById(R.id.spinnerExercicios);
        btnSalvarExecucao = findViewById(R.id.btnSalvarExecucao);
        listaExecucoes = findViewById(R.id.listaExecucoes);

        edtDataExecucao.setFocusable(false);
        edtDataExecucao.setClickable(true);

        edtDataExecucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirSeletorData();
            }
        });

        execucaoAppDAO = new ExecucaoAppDAO(this);
        exercicioDAO = new ExercicioDAO(this);

        carregarSpinnerExercicios();
        carregarListaExecucoes();

        btnSalvarExecucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = edtDataExecucao.getText().toString().trim();
                String cargaTexto = edtCargaExecucao.getText().toString().trim();
                String observacao = edtObservacaoExecucao.getText().toString().trim();

                if (data.isEmpty() || cargaTexto.isEmpty()) {
                    Toast.makeText(ExecucaoActivity.this, "Preencha data e carga", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exercicios == null || exercicios.isEmpty()) {
                    Toast.makeText(ExecucaoActivity.this, "Cadastre um exercício primeiro", Toast.LENGTH_SHORT).show();
                    return;
                }

                double carga = Double.parseDouble(cargaTexto);
                int posicaoExercicio = spinnerExercicios.getSelectedItemPosition();
                int exercicioId = exercicios.get(posicaoExercicio).getId();

                ExecucaoApp execucao = new ExecucaoApp(data, carga, observacao, exercicioId);
                long resultado = execucaoAppDAO.inserir(execucao);

                if (resultado != -1) {
                    Toast.makeText(ExecucaoActivity.this, "Execução salva com sucesso", Toast.LENGTH_SHORT).show();
                    edtDataExecucao.setText("");
                    edtCargaExecucao.setText("");
                    edtObservacaoExecucao.setText("");
                    carregarListaExecucoes();
                } else {
                    Toast.makeText(ExecucaoActivity.this, "Erro ao salvar execução", Toast.LENGTH_SHORT).show();
                }
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

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    edtDataExecucao.setText(formato.format(dataSelecionada.getTime()));
                },
                ano,
                mes,
                dia
        );

        datePickerDialog.show();
    }

    private void carregarSpinnerExercicios() {
        exercicios = exercicioDAO.listar();
        nomesExercicios = new ArrayList<>();

        for (Exercicio exercicio : exercicios) {
            nomesExercicios.add(exercicio.getNome());
        }

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, nomesExercicios);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerExercicios.setAdapter(spinnerAdapter);
    }

    private void carregarListaExecucoes() {
        execucoes = execucaoAppDAO.listar();

        Map<Integer, String> mapaExercicios = new HashMap<>();
        exercicios = exercicioDAO.listar();

        for (Exercicio exercicio : exercicios) {
            mapaExercicios.put(exercicio.getId(), exercicio.getNome());
        }

        execucaoAdapter = new ExecucaoAdapter(this, execucoes, mapaExercicios, this);
        listaExecucoes.setAdapter(execucaoAdapter);
    }

    @Override
    public void onExcluirClick(final ExecucaoApp execucao) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir execução")
                .setMessage("Deseja excluir esta execução do dia \"" + execucao.getData() + "\"?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int resultado = execucaoAppDAO.excluir(execucao.getId());

                        if (resultado > 0) {
                            Toast.makeText(ExecucaoActivity.this, "Execução excluída com sucesso", Toast.LENGTH_SHORT).show();
                            carregarListaExecucoes();
                        } else {
                            Toast.makeText(ExecucaoActivity.this, "Erro ao excluir execução", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}