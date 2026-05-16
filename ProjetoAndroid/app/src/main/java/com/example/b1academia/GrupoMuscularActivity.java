package com.example.b1academia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b1academia.adapter.GrupoMuscularAdapter;
import com.example.b1academia.dao.ExercicioDAO;
import com.example.b1academia.dao.GrupoMuscularDAO;
import com.example.b1academia.model.Exercicio;
import com.example.b1academia.model.GrupoMuscular;

import java.util.List;

public class GrupoMuscularActivity extends AppCompatActivity implements GrupoMuscularAdapter.OnExcluirClickListener {
    private EditText edtNomeGrupo;
    private Button btnSalvarGrupo;
    private ListView listaGrupos;
    private GrupoMuscularDAO grupoMuscularDAO;
    private ExercicioDAO exercicioDAO;
    private GrupoMuscularAdapter adapter;
    private List<GrupoMuscular> grupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_muscular);

        edtNomeGrupo = findViewById(R.id.edtNomeGrupo);
        btnSalvarGrupo = findViewById(R.id.btnSalvarGrupo);
        listaGrupos = findViewById(R.id.listaGrupos);

        grupoMuscularDAO = new GrupoMuscularDAO(this);
        exercicioDAO = new ExercicioDAO(this);

        carregarLista();

        btnSalvarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNomeGrupo.getText().toString().trim();

                if (nome.isEmpty()) {
                    Toast.makeText(GrupoMuscularActivity.this, "Digite o nome do grupo", Toast.LENGTH_SHORT).show();
                } else {
                    GrupoMuscular grupo = new GrupoMuscular(nome);
                    long resultado = grupoMuscularDAO.inserir(grupo);

                    if (resultado != -1) {
                        Toast.makeText(GrupoMuscularActivity.this, "Grupo salvo com sucesso", Toast.LENGTH_SHORT).show();
                        edtNomeGrupo.setText("");
                        carregarLista();
                    } else {
                        Toast.makeText(GrupoMuscularActivity.this, "Erro ao salvar grupo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void carregarLista() {
        grupos = grupoMuscularDAO.listar();
        adapter = new GrupoMuscularAdapter(this, grupos, this);
        listaGrupos.setAdapter(adapter);
    }

    @Override
    public void onExcluirClick(final GrupoMuscular grupo) {
        List<Exercicio> exercicios = exercicioDAO.listar();

        for (Exercicio exercicio : exercicios) {
            if (exercicio.getGrupoId() == grupo.getId()) {
                Toast.makeText(this, "Não é possível excluir. Este grupo possui exercícios vinculados.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Excluir grupo")
                .setMessage("Deseja excluir o grupo \"" + grupo.getNome() + "\"?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int resultado = grupoMuscularDAO.excluir(grupo.getId());

                        if (resultado > 0) {
                            Toast.makeText(GrupoMuscularActivity.this, "Grupo excluído com sucesso", Toast.LENGTH_SHORT).show();
                            carregarLista();
                        } else {
                            Toast.makeText(GrupoMuscularActivity.this, "Erro ao excluir grupo", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}