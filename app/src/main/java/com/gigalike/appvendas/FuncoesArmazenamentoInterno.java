package com.gigalike.appvendas;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FuncoesArmazenamentoInterno {
    InputStreamReader inputStreamReader = null;
    private final String nomeArquivoProdutos = "appVendasProdutos";
    private final String nomeSharPrefUltimaAtualizacao = "appVendasDataATualiazacao";
    private final String nomeSharPrefDadosCliente = "dadosCliente";

    //Gson é uma ferramenta feito importação via build.grandle para trabalhar com Json
    private Gson gson = new Gson();

    //PROPRIEDADE nomeArquivoProdutos
    public String getNomeArquivoProdutos() {
        return nomeArquivoProdutos;
    }

    //PROPRIEDADE nomeArquivoUltimaAtualizacao
    public String getNomeSharPrefUltimaAtualizacao() {return nomeSharPrefUltimaAtualizacao;}

    //CONSTRUTOR
    public FuncoesArmazenamentoInterno() {}



/*    public void excluirArquivoArmazenamentoInterno(Context context) {
        try {
            File dir = context.getFilesDir();
            File file = new File(dir, this.getNomeArquivoProdutos());
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean jaExisteArquivoProdutos(Context context, String nomeArquivo){
        File file = context.getFileStreamPath(nomeArquivo);
        return file.exists();
    }*/


    /*public void salvarProdutosNoArmazenamentoInterno(ArrayList<ModelProduto>arrayListProdutos, Context context){
        try {for (ModelProduto modelProduto :arrayListProdutos) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(this.nomeArquivoProdutos,Context.MODE_APPEND));
                outputStreamWriter.write(gson.toJson(modelProduto));
                outputStreamWriter.write("\n");
                outputStreamWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


/*    public ArrayList<ModelProduto> leituraDeProdutosDoArmazenamentoInterno(Context context) {
        ModelProduto modelProduto;
        ArrayList<ModelProduto> arrayListModelProduto = new ArrayList<ModelProduto>();
        try {
            //abrir arquivo
            File file = context.getFileStreamPath(nomeArquivoProdutos);
            if (file != null && file.exists()) {
                InputStream arquivo = context.openFileInput(nomeArquivoProdutos);
                if (arquivo != null) {
                    //ler arquivo
                    inputStreamReader = new InputStreamReader(arquivo);
                    //gerar buffer
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    //recuperar textos
                    String linhaArquivo = "";
                    while ((linhaArquivo = bufferedReader.readLine()) != null) {
                        modelProduto = gson.fromJson(linhaArquivo, ModelProduto.class);
                        arrayListModelProduto.add(modelProduto);
                    }
                    arquivo.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayListModelProduto;
    }//public ArrayList<ProdutoModel> obterArrayDeProdutos*/



    //Esta função faz a leitura do arquivo json cidadesJson.json que coloquei
    //na pasta assets que eu criei. Ele contém todas cidades, Unidade Federativa e codigo IBGE do Brasil
    public String lerCidadesJson(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }




    public void sharedPrefSalvar(String sharPrefChave, String sharPrefValor, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharPrefChave,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharPrefChave,sharPrefValor);
        editor.apply();
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




    public boolean sharPrefExiste(String sharPrefChave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharPrefChave,MODE_PRIVATE);
        return sharedPreferences.contains(sharPrefChave);
    }




    public void salvarDadosClienteNoArmazenamentoInterno(ModelCliente clienteModel, Activity activity){
        try {
                String jsonClienteDados = gson.toJson(clienteModel);
                sharedPrefSalvar(nomeSharPrefDadosCliente,jsonClienteDados,activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public ModelCliente leituraDadosClienteDoArmazenamentoInterno(Activity activity) {
        ModelCliente clienteModel = new ModelCliente();
        try {
                String strRegistro = sharPrefLeitura(nomeSharPrefDadosCliente,activity);
                clienteModel = gson.fromJson(strRegistro, ModelCliente.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteModel;
    }








}//public class FuncoesArmazenamentoInterno
