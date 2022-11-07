package com.gigalike.appvendas;

//*****************************************************
//Classe de acesso ao SQLITE para leitura e gravação dos produtos
//Abaixo como criar o banco de dados
//https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/
//*****************************************************

import static com.gigalike.appvendas.CidadesSqlite.COLUMN_CIDADE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProdutosSqlite extends SQLiteOpenHelper {
    private static final String DB_NAME = "appVendasProdutos";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "appVendasProdutos";

    public static final String COLUMN_PK_ID_PRODUTO = "pk_id_Produto";
    public static final String  COLUMN_CODIGO = "codigo";
    public static final String  COLUMN_DESCRICAO = "descricao";
    public static final String  COLUMN_COD_BARRAS = "cod_barras";
    public static final String  COLUMN_ESTOQUE = "estoque";
    public static final String  COLUMN_PVENDA = "pvenda";
    public static final String  COLUMN_DEPARTAMENTO = "departamento";
    public static final String  COLUMN_PLIQUIDO = "pliquido";
    public static final String  COLUMN_PBRUTO = "pbruto";
    public static final String  COLUMN_NCM = "ncm";
    public static final String  COLUMN_TAMANHO = "tamanho";
    public static final String  COLUMN_PCUSTO = "pcusto";
    public static final String  COLUMN_UNIDADE = "unidade";
    public static final String  COLUMN_GONDOLA = "gondola";
    public static final String  COLUMN_FAMILIA = "familia";
    public static final String  COLUMN_LOCALIZACAO = "localizacao";
    public static final String  COLUMN_CST = "cst";
    public static final String  COLUMN_ALIQ_ICMS = "aliq_icms";
    public static final String  COLUMN_CEST = "cest";
    public static final String  COLUMN_PISCOF = "piscof";
    public static final String  COLUMN_OBSERVACAO = "observacao";
    public static final String  COLUMN_ATUALIZADO = "atualizado";
    public static final String  COLUMN_SITUACAO = "situacao";
    public static final String COLUMN_SELECIONADO =  "selecionado";


    public ProdutosSqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //dropTableProdutos();
        String query =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_NAME + " (" +
                        COLUMN_PK_ID_PRODUTO + " INTEGER PRIMARY KEY," +
                        COLUMN_CODIGO + " TEXT,"+
                        COLUMN_DESCRICAO + " TEXT,"+
                        COLUMN_COD_BARRAS + " TEXT,"+
                        COLUMN_ESTOQUE + " TEXT,"+
                        COLUMN_PVENDA + " TEXT,"+
                        COLUMN_DEPARTAMENTO + " TEXT,"+
                        COLUMN_PLIQUIDO + " TEXT,"+
                        COLUMN_PBRUTO + " TEXT,"+
                        COLUMN_NCM + " TEXT,"+
                        COLUMN_TAMANHO + " TEXT,"+
                        COLUMN_PCUSTO + " TEXT,"+
                        COLUMN_UNIDADE + " TEXT,"+
                        COLUMN_GONDOLA + " TEXT,"+
                        COLUMN_FAMILIA + " TEXT,"+
                        COLUMN_LOCALIZACAO + " TEXT,"+
                        COLUMN_CST + " TEXT,"+
                        COLUMN_ALIQ_ICMS + " TEXT,"+
                        COLUMN_CEST + " TEXT,"+
                        COLUMN_PISCOF + " TEXT,"+
                        COLUMN_OBSERVACAO + " TEXT,"+
                        COLUMN_ATUALIZADO + " TEXT,"+
                        //Abaixo: Como SQLite não possui Boolean usei um INTEGER, se true = 1 senão 0
                        COLUMN_SITUACAO + " TEXT,"+
                        COLUMN_SELECIONADO + " INTEGER)";
        db.execSQL(query);
    }//public void onCreate


    public void inserirProdutos(List<ModelProduto> modelProdutoList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (ModelProduto modelProduto: modelProdutoList) {
            values.put(COLUMN_CODIGO,modelProduto.getCodigo() == null ? "" : modelProduto.getCodigo());
            values.put(COLUMN_DESCRICAO,modelProduto.getDescricao() == null ? "" : modelProduto.getDescricao());
            values.put(COLUMN_COD_BARRAS,modelProduto.getCod_barras() == null ? "" : modelProduto.getCod_barras());
            values.put(COLUMN_ESTOQUE,modelProduto.getEstoque() == null ? "" : modelProduto.getEstoque());
            values.put(COLUMN_PVENDA,modelProduto.getPvenda() == null ? "" : modelProduto.getPvenda());
            values.put(COLUMN_DEPARTAMENTO,modelProduto.getDepartamento() == null ? "" : modelProduto.getDepartamento());
            values.put(COLUMN_PLIQUIDO,modelProduto.getPliquido() == null ? "" : modelProduto.getPliquido());
            values.put(COLUMN_PBRUTO,modelProduto.getPbruto() == null ? "" : modelProduto.getPbruto());
            values.put(COLUMN_NCM,modelProduto.getNcm() == null ? "" : modelProduto.getNcm());
            values.put(COLUMN_TAMANHO,modelProduto.getTamanho() == null ? "" : modelProduto.getTamanho());
            values.put(COLUMN_PCUSTO,modelProduto.getPcusto() == null ? "" : modelProduto.getPcusto());
            values.put(COLUMN_UNIDADE,modelProduto.getUnidade() == null ? "" : modelProduto.getUnidade());
            values.put(COLUMN_GONDOLA,modelProduto.getGondola() == null ? "" : modelProduto.getGondola());
            values.put(COLUMN_FAMILIA,modelProduto.getFamilia() == null ? "" : modelProduto.getFamilia());
            values.put(COLUMN_LOCALIZACAO,modelProduto.getLocalizacao() == null ? "" : modelProduto.getLocalizacao());
            values.put(COLUMN_CST,modelProduto.getCst() == null ? "" : modelProduto.getCst());
            values.put(COLUMN_ALIQ_ICMS,modelProduto.getAliq_icms() == null ? "" : modelProduto.getAliq_icms());
            values.put(COLUMN_CEST,modelProduto.getCest() == null ? "" : modelProduto.getCest());
            values.put(COLUMN_PISCOF,modelProduto.getPiscof() == null ? "" : modelProduto.getPiscof());
            values.put(COLUMN_OBSERVACAO,modelProduto.getObservacao() == null ? "" : modelProduto.getObservacao());
            values.put(COLUMN_ATUALIZADO,modelProduto.getAtualizado() == null ? "" : modelProduto.getAtualizado());
            values.put(COLUMN_SITUACAO,modelProduto.getSituacao() == null ? "" : modelProduto.getSituacao());
            //Abaixo: Como SQLite não possui Boolean usei um if ternário se true = 1 senão 0
            values.put(COLUMN_SELECIONADO,modelProduto.getSelecionado() ? 1 : 0);
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }// public void inserirProdutos



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }




    public ArrayList<ModelProduto> buscaProdutos(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ModelProduto> modelProdutoList = new ArrayList<>();
        Cursor cursorProdutos = db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);

        //Abaixo foi criado uma variavel para guardar o indice de cada campo da tabela para facilitar na leitura
        int indiceCOLUMN_CODIGO = cursorProdutos.getColumnIndex(COLUMN_CODIGO);
        int indiceCOLUMN_DESCRICAO = cursorProdutos.getColumnIndex(COLUMN_DESCRICAO);
        int indiceCOLUMN_COD_BARRAS = cursorProdutos.getColumnIndex(COLUMN_COD_BARRAS);
        int indiceCOLUMN_ESTOQUE = cursorProdutos.getColumnIndex(COLUMN_ESTOQUE);
        int indiceCOLUMN_PVENDA = cursorProdutos.getColumnIndex(COLUMN_PVENDA);
        int indiceCOLUMN_DEPARTAMENTO = cursorProdutos.getColumnIndex(COLUMN_DEPARTAMENTO);
        int indiceCOLUMN_PLIQUIDO = cursorProdutos.getColumnIndex(COLUMN_PLIQUIDO);
        int indiceCOLUMN_PBRUTO = cursorProdutos.getColumnIndex(COLUMN_PBRUTO);
        int indiceCOLUMN_NCM = cursorProdutos.getColumnIndex(COLUMN_NCM);
        int indiceCOLUMN_TAMANHO = cursorProdutos.getColumnIndex(COLUMN_TAMANHO);
        int indiceCOLUMN_PCUSTO = cursorProdutos.getColumnIndex(COLUMN_PCUSTO);
        int indiceCOLUMN_UNIDADE = cursorProdutos.getColumnIndex(COLUMN_UNIDADE);
        int indiceCOLUMN_GONDOLA = cursorProdutos.getColumnIndex(COLUMN_GONDOLA);
        int indiceCOLUMN_FAMILIA = cursorProdutos.getColumnIndex(COLUMN_FAMILIA);
        int indiceCOLUMN_LOCALIZACAO = cursorProdutos.getColumnIndex(COLUMN_LOCALIZACAO);
        int indiceCOLUMN_CST = cursorProdutos.getColumnIndex(COLUMN_CST);
        int indiceCOLUMN_ALIQ_ICMS = cursorProdutos.getColumnIndex(COLUMN_ALIQ_ICMS);
        int indiceCOLUMN_CEST = cursorProdutos.getColumnIndex(COLUMN_CEST);
        int indiceCOLUMN_PISCOF = cursorProdutos.getColumnIndex(COLUMN_PISCOF);
        int indiceCOLUMN_OBSERVACAO = cursorProdutos.getColumnIndex(COLUMN_OBSERVACAO);
        int indiceCOLUMN_ATUALIZADO = cursorProdutos.getColumnIndex(COLUMN_ATUALIZADO);
        int indiceCOLUMN_SITUACAO = cursorProdutos.getColumnIndex(COLUMN_SITUACAO);
        int indiceCOLUMN_SELECIONADO = cursorProdutos.getColumnIndex(COLUMN_SELECIONADO);


        if (cursorProdutos.moveToFirst()) {
            do {
                modelProdutoList.add(new ModelProduto(
                        cursorProdutos.getString(indiceCOLUMN_CODIGO),
                        cursorProdutos.getString(indiceCOLUMN_DESCRICAO),
                        cursorProdutos.getString(indiceCOLUMN_COD_BARRAS),
                        cursorProdutos.getString(indiceCOLUMN_ESTOQUE),
                        cursorProdutos.getString(indiceCOLUMN_PVENDA),
                        cursorProdutos.getString(indiceCOLUMN_DEPARTAMENTO),
                        cursorProdutos.getString(indiceCOLUMN_PLIQUIDO),
                        cursorProdutos.getString(indiceCOLUMN_PBRUTO),
                        cursorProdutos.getString(indiceCOLUMN_NCM),
                        cursorProdutos.getString(indiceCOLUMN_TAMANHO),
                        cursorProdutos.getString(indiceCOLUMN_PCUSTO),
                        cursorProdutos.getString(indiceCOLUMN_UNIDADE),
                        cursorProdutos.getString(indiceCOLUMN_GONDOLA),
                        cursorProdutos.getString(indiceCOLUMN_FAMILIA),
                        cursorProdutos.getString(indiceCOLUMN_LOCALIZACAO),
                        cursorProdutos.getString(indiceCOLUMN_CST),
                        cursorProdutos.getString(indiceCOLUMN_ALIQ_ICMS),
                        cursorProdutos.getString(indiceCOLUMN_CEST),
                        cursorProdutos.getString(indiceCOLUMN_PISCOF),
                        cursorProdutos.getString(indiceCOLUMN_OBSERVACAO),
                        cursorProdutos.getString(indiceCOLUMN_ATUALIZADO),
                        cursorProdutos.getString(indiceCOLUMN_SITUACAO),
                        cursorProdutos.getInt(indiceCOLUMN_SELECIONADO)
                ));
            } while (cursorProdutos.moveToNext());
            // moving our cursor to next.
        }
        cursorProdutos.close();
        return modelProdutoList;
    }


    public void dropTableProdutos(){
        System.out.println("### dropTableProdutos");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean tabelaExiste(){
        boolean retorno = false;
        try {
            String countQuery = "SELECT name FROM sqlite_master WHERE name='"+TABLE_NAME+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            if (!cursor.moveToFirst())
            {
                cursor.close();
            }else {
                int count = cursor.getInt(0);
                cursor.close();
                retorno = true;
                System.out.println("### tabelaExiste; count:"+count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }




}//public class ProdutosSqlite
