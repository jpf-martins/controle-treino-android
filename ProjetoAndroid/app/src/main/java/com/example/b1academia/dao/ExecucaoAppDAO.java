package com.example.b1academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.b1academia.bd.DBHelper;
import com.example.b1academia.model.ExecucaoApp;

import java.util.ArrayList;
import java.util.List;

public class ExecucaoAppDAO {

    private DBHelper dbHelper;

    public ExecucaoAppDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long inserir(ExecucaoApp execucao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_EXECUCAO_DATA, execucao.getData());
        values.put(DBHelper.COL_EXECUCAO_CARGA, execucao.getCarga());
        values.put(DBHelper.COL_EXECUCAO_OBSERVACAO, execucao.getObservacao());
        values.put(DBHelper.COL_EXECUCAO_EXERCICIO_ID, execucao.getExercicioId());

        long resultado = db.insert(DBHelper.TABELA_EXECUCAO, null, values);
        db.close();
        return resultado;
    }

    public List<ExecucaoApp> listar() {
        List<ExecucaoApp> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABELA_EXECUCAO, null);

        if (cursor.moveToFirst()) {
            do {
                ExecucaoApp execucao = new ExecucaoApp();
                execucao.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXECUCAO_ID)));
                execucao.setData(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_EXECUCAO_DATA)));
                execucao.setCarga(cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COL_EXECUCAO_CARGA)));
                execucao.setObservacao(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_EXECUCAO_OBSERVACAO)));
                execucao.setExercicioId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXECUCAO_EXERCICIO_ID)));
                lista.add(execucao);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public int atualizar(ExecucaoApp execucao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_EXECUCAO_DATA, execucao.getData());
        values.put(DBHelper.COL_EXECUCAO_CARGA, execucao.getCarga());
        values.put(DBHelper.COL_EXECUCAO_OBSERVACAO, execucao.getObservacao());
        values.put(DBHelper.COL_EXECUCAO_EXERCICIO_ID, execucao.getExercicioId());

        int resultado = db.update(
                DBHelper.TABELA_EXECUCAO,
                values,
                DBHelper.COL_EXECUCAO_ID + "=?",
                new String[]{String.valueOf(execucao.getId())}
        );

        db.close();
        return resultado;
    }

    public int excluir(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int resultado = db.delete(
                DBHelper.TABELA_EXECUCAO,
                DBHelper.COL_EXECUCAO_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return resultado;
    }
}