package com.example.b1academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.b1academia.bd.DBHelper;
import com.example.b1academia.model.GrupoMuscular;

import java.util.ArrayList;
import java.util.List;

public class GrupoMuscularDAO {

    private DBHelper dbHelper;

    public GrupoMuscularDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long inserir(GrupoMuscular grupo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_GRUPO_NOME, grupo.getNome());

        long resultado = db.insert(DBHelper.TABELA_GRUPO, null, values);
        db.close();
        return resultado;
    }

    public List<GrupoMuscular> listar() {
        List<GrupoMuscular> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABELA_GRUPO, null);

        if (cursor.moveToFirst()) {
            do {
                GrupoMuscular grupo = new GrupoMuscular();
                grupo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_GRUPO_ID)));
                grupo.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_GRUPO_NOME)));
                lista.add(grupo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public int atualizar(GrupoMuscular grupo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_GRUPO_NOME, grupo.getNome());

        int resultado = db.update(
                DBHelper.TABELA_GRUPO,
                values,
                DBHelper.COL_GRUPO_ID + "=?",
                new String[]{String.valueOf(grupo.getId())}
        );

        db.close();
        return resultado;
    }

    public int excluir(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int resultado = db.delete(
                DBHelper.TABELA_GRUPO,
                DBHelper.COL_GRUPO_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return resultado;
    }
}