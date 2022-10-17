package com.gigalike.appvendas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FuncoesArmazenamentoInterno {
    private final String nomeArquivoProdutos = "appVendasProdutos.txt";
    private final String nomeSharPrefUltimaAtualizacao = "appVendasDataATualiazacao";

    //PROPRIEDADE nomeArquivoProdutos
    public String getNomeArquivoProdutos() {
        return nomeArquivoProdutos;
    }

    //PROPRIEDADE nomeArquivoUltimaAtualizacao
    public String getNomeSharPrefUltimaAtualizacao() {return nomeSharPrefUltimaAtualizacao;}

    //CONSTRUTOR
    public FuncoesArmazenamentoInterno() {}


    public void salvarProdutosNoArmazenamentoInterno(String produtoDadosJson, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(this.nomeArquivoProdutos,Context.MODE_APPEND));
            outputStreamWriter.write(produtoDadosJson);
            outputStreamWriter.write("\n");
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean jaExisteArquivoProdutos(Context context, String nomeArquivo){
        File file = context.getFileStreamPath(nomeArquivoProdutos);
        return file.exists();
    }


    public ArrayList<ProdutoModel> obterArrayDeProdutos(Context context) {
        String resultado = "";
        ProdutoModel produtoModel;
        String[] temp;
        ArrayList<ProdutoModel> arrayListProdutoModel = new ArrayList<ProdutoModel>();
        try {
            //abrir arquivo
            File file = context.getFileStreamPath(nomeArquivoProdutos);
            if (file != null && file.exists()) {
                InputStream arquivo = context.openFileInput(nomeArquivoProdutos);
                if (arquivo != null) {
                    //ler arquivo
                    InputStreamReader inputStreamReader = new InputStreamReader(arquivo);
                    //gerar buffer
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    //recuperar textos
                    String linhaArquivo = "";
                    while ((linhaArquivo = bufferedReader.readLine()) != null) {
                        JSONObject produtoJsonObject = new JSONObject(linhaArquivo);
                        produtoModel = FuncoesCompartilhadas.gerarProdutoModel(produtoJsonObject);
                        arrayListProdutoModel.add(produtoModel);
                    }
                    arquivo.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayListProdutoModel;
    }//public ArrayList<ProdutoModel> obterArrayDeProdutos







    public void sharedPrefSalvar(String sharPrefChave, String sharPrefValor, Context context){
        if (!sharPrefExiste(sharPrefChave,context)){
            SharedPreferences sharedPreferences = context.getSharedPreferences(sharPrefChave,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharPrefChave,sharPrefValor);
            editor.apply();
        }
    }


    public boolean sharPrefExiste(String sharPrefChave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharPrefChave,MODE_PRIVATE);
        return sharedPreferences.contains(sharPrefChave);
    }


    public String sharPrefLeitura(String sharPrefChave,Context context){
        String retorno = "";
        try {
            if (sharPrefExiste(sharPrefChave,context)){
                SharedPreferences preferences = context.getSharedPreferences(sharPrefChave, MODE_PRIVATE);
                retorno = preferences.getString(sharPrefChave,"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }










}//public class FuncoesArmazenamentoInterno
