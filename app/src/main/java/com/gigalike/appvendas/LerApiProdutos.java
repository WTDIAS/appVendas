package com.gigalike.appvendas;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class LerApiProdutos {
    private ArrayList<ModelProduto> modelProdutoList = new ArrayList<>();
    private ModelProduto modelProduto = null;
    private RequestQueue mQueue;
    public Activity activity;
    private JSONObject jsonObjectResponse = null;
    private JSONObject jsonObjectProdutoDados = new JSONObject();
    private Gson gson = new Gson();

    //CONSTRUTOR
    public LerApiProdutos(Activity _activity){
        this.activity = _activity;
    }

    public void lerTodosProdutos(String url,MinhasInterfaces.QuandoApiRetornarTodosrProdutos quandoApiRetornarTodosrProdutos){
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
                                    //ABAIXO TRANSFORMO DE OBJETO JSON PARA ProdutoModel
                                    modelProduto = gson.fromJson(String.valueOf(jsonObjectProdutoDados), ModelProduto.class);
                                    modelProdutoList.add(modelProduto);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //ABAIXO SERÁ RETORNADO A LISTA DE PRODUTOS PARA O MÉTODO UTILIZANDO A INTERFACE QUE CHAMOU.
                            quandoApiRetornarTodosrProdutos.retornoApiTodosProdutos(modelProdutoList);
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
