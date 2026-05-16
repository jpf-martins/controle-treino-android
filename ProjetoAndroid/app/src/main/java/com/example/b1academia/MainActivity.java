package com.example.b1academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b1academia.bd.DBHelper;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private Button btnGrupos;
    private Button btnExercicios;
    private Button btnExecucoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        dbHelper.getWritableDatabase();

        btnGrupos = findViewById(R.id.btnGrupos);
        btnExercicios = findViewById(R.id.btnExercicios);
        btnExecucoes = findViewById(R.id.btnExecucoes);

        btnExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExercicioActivity.class);
                startActivity(intent);
            }
        });

        btnGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GrupoMuscularActivity.class);
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