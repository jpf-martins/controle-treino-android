package com.example.b1academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.b1academia.bd.DBHelper;
import com.example.b1academia.model.Exercicio;

import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO {

    private DBHelper dbHelper;

    public ExercicioDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long inserir(Exercicio exercicio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_EXERCICIO_NOME, exercicio.getNome());
        values.put(DBHelper.COL_EXERCICIO_SERIES, exercicio.getSeries());
        values.put(DBHelper.COL_EXERCICIO_REPETICOES, exercicio.getRepeticoes());
        values.put(DBHelper.COL_EXERCICIO_GRUPO_ID, exercicio.getGrupoId());

        long resultado = db.insert(DBHelper.TABELA_EXERCICIO, null, values);
        db.close();
        return resultado;
    }

    public List<Exercicio> listar() {
        List<Exercicio> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABELA_EXERCICIO, null);

        if (cursor.moveToFirst()) {
            do {
                Exercicio exercicio = new Exercicio();
                exercicio.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXERCICIO_ID)));
                exercicio.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_EXERCICIO_NOME)));
                exercicio.setSeries(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXERCICIO_SERIES)));
                exercicio.setRepeticoes(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXERCICIO_REPETICOES)));
                exercicio.setGrupoId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXERCICIO_GRUPO_ID)));
                lista.add(exercicio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public int atualizar(Exercicio exercicio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_EXERCICIO_NOME, exercicio.getNome());
        values.put(DBHelper.COL_EXERCICIO_SERIES, exercicio.getSeries());
        values.put(DBHelper.COL_EXERCICIO_REPETICOES, exercicio.getRepeticoes());
        values.put(DBHelper.COL_EXERCICIO_GRUPO_ID, exercicio.getGrupoId());

        int resultado = db.update(
                DBHelper.TABELA_EXERCICIO,
                values,
                DBHelper.COL_EXERCICIO_ID + "=?",
                new String[]{String.valueOf(exercicio.getId())}
        );

        db.close();
        return resultado;
    }

    public int excluir(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int resultado = db.delete(
                DBHelper.TABELA_EXERCICIO,
                DBHelper.COL_EXERCICIO_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return resultado;
    }
}