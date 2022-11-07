package com.gigalike.appvendas;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FuncoesCompartilhadas {
    public static AdapterProdutos adapterProdutos;
    public static AdapterCidades adapterCidades;

    //Abaixo exibe na tela os produtos contidos no ArrayList
    public static void exibirProdutosNaTela(ArrayList<ModelProduto> modelProdutoList, Activity activity) {
        if (modelProdutoList != null){
            //EXCLUINDO OS DADOS SALVOS NO ARMAZENAMENTO INTERNO ANTES DE SALVAR NOVOS
            FuncoesArmazenamentoInterno funcoesArmazenamentoInterno = new FuncoesArmazenamentoInterno();
            adapterProdutos = new AdapterProdutos(modelProdutoList,activity);
            RecyclerView recyclerViewProdutos = activity.findViewById(R.id.recyclerView_produtos);
            recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(activity));
            recyclerViewProdutos.setAdapter(adapterProdutos);
            //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO NO ARQUIVO SHARPREF DO ARMAZENAMENTO INTERNO
            funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(), FuncoesCompartilhadas.dataATual(), activity);
            esconderProgressBar(activity);
            TextView textViewTotal = activity.findViewById(R.id.textViewTotal);
            textViewTotal.setText(""+ modelProdutoList.size());
        }
    }//private void exibirProdutosNaTela()


    public static void exibirCidadesNaTela(ArrayList<ModelCidade> modelCidadeList, Activity activity){
        if (modelCidadeList != null){
            adapterCidades = new AdapterCidades(modelCidadeList,activity);
            RecyclerView recyclerViewCidades = activity.findViewById(R.id.recyclerViewCidades);
            recyclerViewCidades.setLayoutManager(new LinearLayoutManager(activity));
            recyclerViewCidades.setAdapter(adapterCidades);
        }
    }


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



