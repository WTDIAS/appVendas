package com.gigalike.appvendas;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class LerApiProdutos {
    private ArrayList<ProdutoModel>produtoModelList = new ArrayList<>();
    private ProdutoModel produtoModel = null;
    private RequestQueue mQueue;
    public Activity activity;
    private JSONObject jsonObjectResponse = null;
    private JSONObject jsonObjectProdutoDados = new JSONObject();

    //CONSTRUTOR
    public LerApiProdutos(Activity _activity){
        this.activity = _activity;
    }

    public void lerTodosProdutos(String url){
        mQueue = Volley.newRequestQueue(activity);
        FuncoesArmazenamentoInterno funcoesArmazenamentoInterno = new FuncoesArmazenamentoInterno();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            for (int i = 0; i < response.length(); i++){
                                try {
                                    //Obtenho uma string contendo os dados do primeiro nó(produto_dados que é um json com todos os dados do produto)
                                    String  strResponse = response.getString(i);
                                    //Crio um objeto json a partir da string obtida anteriormente
                                    jsonObjectResponse = new JSONObject(strResponse);
                                    //Obtenho somente os dados do produto em formato json que esta em produto_dados
                                    jsonObjectProdutoDados = jsonObjectResponse.getJSONObject("produto_dados");
                                    //Abaixo é salvo no armazenamento interno do smartphone os produtos para futuras consultas aos produtos
                                    //utilizando a consulta da API somente quando for necessario.
                                    funcoesArmazenamentoInterno.salvarProdutosNoArmazenamentoInterno(jsonObjectProdutoDados.toString(),activity);
                                    //Abaixo a funcao gerarProdutoModel devolve um produtoModel preenchido com dados passados em jsonObjectProdutoDados
                                    produtoModel = FuncoesCompartilhadas.gerarProdutoModel(jsonObjectProdutoDados);
                                    produtoModelList.add(produtoModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //Devido ao fato deste método ser assincrono, ele estava retornando objeto vazio pois somente quando
                            //a API responde é que ele executa este onResponse. Então criei o exibirProdutosNaTela que será chamado
                            //somente quando executar este metodo.
                            FuncoesCompartilhadas.exibirProdutosNaTela(produtoModelList,activity);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }//public ArrayList<ProdutoModel> lerTodosProdutos

}//class LerApiProdutos
