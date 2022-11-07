package com.gigalike.appvendas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

//*****************************************************
//Classe de acesso ao SQLITE para leitura das cidades
//Abaixo como criar o banco de dados
//https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/
//*****************************************************

public class CidadesSqlite extends SQLiteOpenHelper{
    private static final String DB_NAME = "appVendas";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "appVendasCidades";
    public static final String COLUMN_PK_ID_CIDADE = "pk_id_cidades";
    public static final String COLUMN_CIDADE = "cidadeNome";
    public static final String COLUMN_IBGE_COD = "cidadeIbge";
    public static final String COLUMN_UnidFed = "cidadeUnidFed";



    public CidadesSqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //dropTableCidades();
        String query =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_PK_ID_CIDADE + " INTEGER PRIMARY KEY," +
                COLUMN_CIDADE + " TEXT," +
                COLUMN_UnidFed + " TEXT," +
                COLUMN_IBGE_COD + " TEXT)";
        db.execSQL(query);
    }//public void onCreate


    public void inserirCidades(List<ModelCidade> modelCidadeList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (ModelCidade cm: modelCidadeList) {
            values.put(COLUMN_CIDADE, cm.getNomeCidade());
            values.put(COLUMN_UnidFed, cm.getUnidadeFederativa());
            values.put(COLUMN_IBGE_COD, cm.getCodigoIBGE());
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }// public void inserirCidades

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }


    public ArrayList<ModelCidade> buscaCidades(String nomeCidade){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ModelCidade> modelCidadeList = new ArrayList<>();
        Cursor cursorCidades = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE cidadeNome LIKE ?", new String[]{"%"+nomeCidade+"%"});
        int indiceCidadeNome = cursorCidades.getColumnIndex(COLUMN_CIDADE);
        int indiceCidadeUnidFed = cursorCidades.getColumnIndex(COLUMN_UnidFed);
        int indiceCidadeCodIbge = cursorCidades.getColumnIndex(COLUMN_IBGE_COD);

        if (cursorCidades.moveToFirst()) {
            do {
                modelCidadeList.add(new ModelCidade(cursorCidades.getString(indiceCidadeNome),
                        cursorCidades.getString(indiceCidadeUnidFed),
                        cursorCidades.getString(indiceCidadeCodIbge)));
            } while (cursorCidades.moveToNext());
            // moving our cursor to next.
        }
        cursorCidades.close();
        return modelCidadeList;
    }


    public void dropTableCidades(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



}//public final class CidadesSqlitle extends SQLiteOpenHelper


