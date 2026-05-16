package com.example.b1academia.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "academia.db";
    private static final int VERSAO_BANCO = 2;

    public static final String TABELA_GRUPO = "grupo_muscular";
    public static final String COL_GRUPO_ID = "id";
    public static final String COL_GRUPO_NOME = "nome";

    public static final String TABELA_EXERCICIO = "exercicio";
    public static final String COL_EXERCICIO_ID = "id";
    public static final String COL_EXERCICIO_NOME = "nome";
    public static final String COL_EXERCICIO_SERIES = "series";
    public static final String COL_EXERCICIO_REPETICOES = "repeticoes";
    public static final String COL_EXERCICIO_GRUPO_ID = "grupo_id";

    public static final String TABELA_EXECUCAO = "execucao_treino";
    public static final String COL_EXECUCAO_ID = "id";
    public static final String COL_EXECUCAO_DATA = "data";
    public static final String COL_EXECUCAO_CARGA = "carga";
    public static final String COL_EXECUCAO_OBSERVACAO = "observacao";
    public static final String COL_EXECUCAO_EXERCICIO_ID = "exercicio_id";

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlGrupo = "CREATE TABLE " + TABELA_GRUPO + " ("
                + COL_GRUPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_GRUPO_NOME + " TEXT NOT NULL"
                + ");";

        String sqlExercicio = "CREATE TABLE " + TABELA_EXERCICIO + " ("
                + COL_EXERCICIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EXERCICIO_NOME + " TEXT NOT NULL, "
                + COL_EXERCICIO_SERIES + " INTEGER NOT NULL, "
                + COL_EXERCICIO_REPETICOES + " INTEGER NOT NULL, "
                + COL_EXERCICIO_GRUPO_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COL_EXERCICIO_GRUPO_ID + ") REFERENCES "
                + TABELA_GRUPO + "(" + COL_GRUPO_ID + ")"
                + ");";

        String sqlExecucao = "CREATE TABLE " + TABELA_EXECUCAO + " ("
                + COL_EXECUCAO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EXECUCAO_DATA + " TEXT NOT NULL, "
                + COL_EXECUCAO_CARGA + " REAL NOT NULL, "
                + COL_EXECUCAO_OBSERVACAO + " TEXT, "
                + COL_EXECUCAO_EXERCICIO_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COL_EXECUCAO_EXERCICIO_ID + ") REFERENCES "
                + TABELA_EXERCICIO + "(" + COL_EXERCICIO_ID + ")"
                + ");";

        db.execSQL(sqlGrupo);
        db.execSQL(sqlExercicio);
        db.execSQL(sqlExecucao);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_EXECUCAO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_EXERCICIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_GRUPO);
        onCreate(db);
    }
}