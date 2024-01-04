package mz.ac.hendrick.listadetarefas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TarefasDB";
    private static final String TABLE_NAME = "Tarefas";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_PRIORIDADE = "prioridade";
    private static final String COLUMN_CATEGORIA = "categoria";
    private static final String COLUMN_EM_ANDAMENTO = "em_andamento";
    private static final String COLUMN_CONCLUIDA = "concluida";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_TAREFA = "tarefa";


    public DBHelper(MainActivity context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DESCRICAO + " TEXT,"
                + COLUMN_PRIORIDADE + " TEXT,"
                + COLUMN_CATEGORIA + " TEXT,"
                + COLUMN_EM_ANDAMENTO + " INTEGER,"
                + COLUMN_CONCLUIDA + " INTEGER,"
                + COLUMN_DATA + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void salvarTarefa(String tarefa, String prioridade, String categoria, boolean emAndamento, boolean concluida, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAREFA, tarefa);
        values.put(COLUMN_PRIORIDADE, prioridade); // Supondo que COLUMN_PRIORIDADE seja o nome do campo de prioridade na tabela
        values.put(COLUMN_CATEGORIA, categoria); // Supondo que COLUMN_CATEGORIA seja o nome do campo de categoria na tabela
        values.put(COLUMN_EM_ANDAMENTO, emAndamento ? 1 : 0); // Supondo que COLUMN_EM_ANDAMENTO seja o nome do campo de status de andamento na tabela
        values.put(COLUMN_CONCLUIDA, concluida ? 1 : 0); // Supondo que COLUMN_CONCLUIDA seja o nome do campo de status concluído na tabela
        values.put(COLUMN_DATA, data); // Supondo que COLUMN_DATA seja o nome do campo de data na tabela

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            // Falha ao inserir
        } else {
            // Inserido com sucesso
        }
        db.close();
    }

}
