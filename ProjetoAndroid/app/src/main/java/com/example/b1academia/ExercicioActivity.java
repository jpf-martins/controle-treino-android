package com.example.b1academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.b1academia.adapter.ExercicioAdapter;
import com.example.b1academia.dao.ExercicioDAO;
import com.example.b1academia.dao.ExecucaoAppDAO;
import com.example.b1academia.dao.GrupoMuscularDAO;
import com.example.b1academia.model.Exercicio;
import com.example.b1academia.model.ExecucaoApp;
import com.example.b1academia.model.GrupoMuscular;

import java.util.ArrayList;
import java.util.List;

public class ExercicioActivity extends AppCompatActivity implements ExercicioAdapter.OnExcluirClickListener {
    private EditText edtNomeExercicio;
    private EditText edtSeries;
    private EditText edtRepeticoes;
    private Spinner spinnerGrupos;
    private Button btnSalvarExercicio;
    private ListView listaExercicios;
    private ExercicioDAO exercicioDAO;
    private GrupoMuscularDAO grupoMuscularDAO;
    private ExecucaoAppDAO execucaoAppDAO;
    private List<GrupoMuscular> grupos;
    private List<String> nomesGrupos;
    private List<Exercicio> exercicios;
    private ExercicioAdapter exercicioAdapter;
    private ArrayAdapter<String> spinnerAdapter;

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

        exercicioDAO = new ExercicioDAO(this);
        grupoMuscularDAO = new GrupoMuscularDAO(this);
        execucaoAppDAO = new ExecucaoAppDAO(this);

        carregarSpinnerGrupos();
        carregarListaExercicios();

        btnSalvarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNomeExercicio.getText().toString().trim();
                String seriesTexto = edtSeries.getText().toString().trim();
                String repeticoesTexto = edtRepeticoes.getText().toString().trim();

                if (nome.isEmpty() || seriesTexto.isEmpty() || repeticoesTexto.isEmpty()) {
                    Toast.makeText(ExercicioActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (grupos == null || grupos.isEmpty()) {
                    Toast.makeText(ExercicioActivity.this, "Cadastre um grupo muscular primeiro", Toast.LENGTH_SHORT).show();
                    return;
                }

                int series = Integer.parseInt(seriesTexto);
                int repeticoes = Integer.parseInt(repeticoesTexto);
                int posicaoGrupo = spinnerGrupos.getSelectedItemPosition();
                int grupoId = grupos.get(posicaoGrupo).getId();

                Exercicio exercicio = new Exercicio(nome, series, repeticoes, grupoId);
                long resultado = exercicioDAO.inserir(exercicio);

                if (resultado != -1) {
                    Toast.makeText(ExercicioActivity.this, "Exercício salvo com sucesso", Toast.LENGTH_SHORT).show();
                    edtNomeExercicio.setText("");
                    edtSeries.setText("");
                    edtRepeticoes.setText("");
                    carregarListaExercicios();
                } else {
                    Toast.makeText(ExercicioActivity.this, "Erro ao salvar exercício", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void carregarSpinnerGrupos() {
        grupos = grupoMuscularDAO.listar();
        nomesGrupos = new ArrayList<>();

        for (GrupoMuscular grupo : grupos) {
            nomesGrupos.add(grupo.getNome());
        }

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, nomesGrupos);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGrupos.setAdapter(spinnerAdapter);
    }

    private void carregarListaExercicios() {
        exercicios = exercicioDAO.listar();
        exercicioAdapter = new ExercicioAdapter(this, exercicios, this);
        listaExercicios.setAdapter(exercicioAdapter);
    }

    @Override
    public void onExcluirClick(final Exercicio exercicio) {
        List<ExecucaoApp> execucoes = execucaoAppDAO.listar();

        for (ExecucaoApp execucao : execucoes) {
            if (execucao.getExercicioId() == exercicio.getId()) {
                Toast.makeText(this, "Não é possível excluir. Este exercício possui execuções vinculadas.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Excluir exercício")
                .setMessage("Deseja excluir o exercício \"" + exercicio.getNome() + "\"?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int resultado = exercicioDAO.excluir(exercicio.getId());

                        if (resultado > 0) {
                            Toast.makeText(ExercicioActivity.this, "Exercício excluído com sucesso", Toast.LENGTH_SHORT).show();
                            carregarListaExercicios();
                        } else {
                            Toast.makeText(ExercicioActivity.this, "Erro ao excluir exercício", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}