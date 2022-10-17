package com.gigalike.appvendas;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FuncoesCompartilhadas {
    public static ProdutosAdapter produtosAdapter;
    //Abaixo ao receber um object json faz a leitura de cada campo para um produtoModel e o retorna
    public static ProdutoModel gerarProdutoModel(@NonNull JSONObject jsonObjectProdutoDados){
        ProdutoModel produtoModel = new ProdutoModel();
        try {
            produtoModel.setCodigo(jsonObjectProdutoDados.getString("codigo"));
            produtoModel.setDescricao(jsonObjectProdutoDados.getString("descricao"));
            produtoModel.setCod_barras(jsonObjectProdutoDados.getString("cod_barras"));
            produtoModel.setEstoque(jsonObjectProdutoDados.getString("estoque"));
            produtoModel.setPvenda("R$ "+jsonObjectProdutoDados.getString("pvenda"));
            produtoModel.setDepartamento(jsonObjectProdutoDados.getString("departamento"));
            produtoModel.setPliquido(jsonObjectProdutoDados.getString("pliquido"));
            produtoModel.setPbruto(jsonObjectProdutoDados.getString("pbruto"));
            produtoModel.setNcm(jsonObjectProdutoDados.getString("ncm"));
            produtoModel.setTamanho(jsonObjectProdutoDados.getString("tamanho"));
            produtoModel.setPcusto(jsonObjectProdutoDados.getString("pcusto"));
            produtoModel.setUnidade(jsonObjectProdutoDados.getString("unidade"));
            produtoModel.setGondola(jsonObjectProdutoDados.getString("gondola"));
            produtoModel.setFamilia(jsonObjectProdutoDados.getString("familia"));
            produtoModel.setLocalizacao(jsonObjectProdutoDados.getString("localizacao"));
            produtoModel.setCst(jsonObjectProdutoDados.getString("cst"));
            produtoModel.setAliq_icms(jsonObjectProdutoDados.getString("aliq_icms"));
            produtoModel.setCest(jsonObjectProdutoDados.getString("cest"));
            produtoModel.setPiscof(jsonObjectProdutoDados.getString("piscof"));
            produtoModel.setObservacao(jsonObjectProdutoDados.getString("observacao"));
            produtoModel.setAtualizado(jsonObjectProdutoDados.getString("atualizado"));
            produtoModel.setSituacao(jsonObjectProdutoDados.getString("situacao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return produtoModel;
    }
    //Abaixo exibe na tela os produtos contidos no ArrayList
    public static void exibirProdutosNaTela(ArrayList<ProdutoModel> produtoModelList, Activity activity) {
        if (produtoModelList != null){
            produtosAdapter = new ProdutosAdapter(produtoModelList,activity);
            RecyclerView recyclerViewProdutos = activity.findViewById(R.id.recyclerView_produtos);
            recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(activity));
            recyclerViewProdutos.setAdapter(produtosAdapter);
            esconderProgressBar(activity);

            TextView textViewTotal = activity.findViewById(R.id.textViewTotal);
            textViewTotal.setText(""+produtoModelList.size());
        }
    }//private void exibirProdutosNaTela()


    public static String dataATual(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dataAtual = sdf.format(Calendar.getInstance().getTime());
        return dataAtual;
    }

    public static void esconderProgressBar(Activity activity){
        ProgressBar myProgressBar;
        myProgressBar = activity.findViewById(R.id.myProgressBar);
        myProgressBar.setVisibility(View.GONE);
    }

}
