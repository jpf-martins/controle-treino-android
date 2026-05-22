package com.example.b1academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnGrupos;
    private Button btnExercicios;
    private Button btnTreinos;
    private Button btnExecucoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrupos = findViewById(R.id.btnGrupos);
        btnExercicios = findViewById(R.id.btnExercicios);
        btnTreinos = findViewById(R.id.btnTreinos);
        btnExecucoes = findViewById(R.id.btnExecucoes);

        btnGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GrupoMuscularActivity.class);
                startActivity(intent);
            }
        });

        btnExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExercicioActivity.class);
                startActivity(intent);
            }
        });

        btnTreinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TreinoActivity.class);
                startActivity(intent);
            }
        });

        btnExecucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExecucaoActivity.class);
                startActivity(intent);
            }
        });
    }
}