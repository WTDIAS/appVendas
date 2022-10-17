package com.gigalike.appvendas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Objects;

//**********************************************************************
//VIDEO IMPLEMENTACAO FILTRO NO RECYRCLERVIEW                          *
//https://www.youtube.com/watch?v=MWlxFccYit8&ab_channel=larntech      *
//VIDEO BOTÃO VOLTAR NA APPBAR                                         *
//https://www.youtube.com/watch?v=JkVdP-e9BCo&ab_channel=CodinginFlow  *
//**********************************************************************


public class ProdutosActivity extends AppCompatActivity {
    //public Activity activity;
    private FuncoesArmazenamentoInterno funcoesArmazenamentoInterno = new FuncoesArmazenamentoInterno();
    private ArrayList<ProdutoModel> arrayListProdutoModel = new ArrayList<>();
    private ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        //BAIXO ESTOU COLOCANDO MEU MENU NA MINHA TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.busca);
        exibeProdutos();
    }// protected void onCreate

    private void exibeProdutos(){
        myProgressBar = findViewById(R.id.myProgressBar);
        //sufixoTabela é o numero colocado ao fim do nome de cada tabela
        //no banco de dados para identificar de qual cliente é a tabela
        String sufixoTabela = "1";
        String url = "https://app-vendas-v3.herokuapp.com/buscarTodosProdutos/"+sufixoTabela;

        //A PRIMEIRA VEZ QUE É ABERTO O APP NO DIA ELE BUSCA O CADASTRO DE PRODUTOS NA API, NAS PROXIMAS ELE BUSCA NO ARMAZENAMENTO LOCAL
        //O IF ELSE ABAIXO VERIFICA SE A DATA DA ATUALIZAÇÃO SALVA NO ARMAZENAMENTO INTERNO É IGUAL A DATA ATUAL
        //SE JA EXISTE A DATA DA ULTIMA ATUALIZAÇÃO VERIFICO SE É IGUAL A DATA ATUAL
        if (funcoesArmazenamentoInterno.sharPrefExiste(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(),ProdutosActivity.this)){
            String diaMesATual = FuncoesCompartilhadas.dataATual().substring(0,5);
            String ultimaAtualizacao = funcoesArmazenamentoInterno.sharPrefLeitura(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(),ProdutosActivity.this);
            String diaMesUltimaAtualizacao = ultimaAtualizacao.substring(0,5);
            //SE ULTIMA ATUALIZAÇÃO FOR IGUAL DATA ATUAL ENTÃO CARREGO OS PRODUTOS DO ARQMAZENAMENTO INTERNO
            if (diaMesUltimaAtualizacao.equals(diaMesATual)){
                //SE EXITE O ARQUIVO DE PRODUTOS SALVO NO ARMAZENAMENTO INTERNO CARREGO
                if (funcoesArmazenamentoInterno.jaExisteArquivoProdutos(ProdutosActivity.this,funcoesArmazenamentoInterno.getNomeArquivoProdutos())){
                    arrayListProdutoModel = funcoesArmazenamentoInterno.obterArrayDeProdutos(ProdutosActivity.this);
                    FuncoesCompartilhadas.exibirProdutosNaTela(arrayListProdutoModel, ProdutosActivity.this);
                //SE NÃO EXITE O ARQUIVO DE PRODUTOS SALVO NO ARMAZENAMENTO INTERNO CARREGO DA API
                }else {
                    LerApiProdutos lerApiProdutos = new LerApiProdutos(ProdutosActivity.this);
                    lerApiProdutos.lerTodosProdutos(url);
                    //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO
                    funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(),FuncoesCompartilhadas.dataATual(),ProdutosActivity.this);
                }
            //SENAO CARREGO OS PRODUTOS DA API
            }else {
                LerApiProdutos lerApiProdutos = new LerApiProdutos(ProdutosActivity.this);
                lerApiProdutos.lerTodosProdutos(url);
                //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO
                funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(),FuncoesCompartilhadas.dataATual(),ProdutosActivity.this);
            }
        }else {
            LerApiProdutos lerApiProdutos = new LerApiProdutos(ProdutosActivity.this);
            lerApiProdutos.lerTodosProdutos(url);
            //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO
            funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(),FuncoesCompartilhadas.dataATual(),ProdutosActivity.this);
        }
    }//private void exibeProdutos()



    private void selecionarItensCheckados() {
        ArrayList<ProdutoModel>arrayListProdutosCheckados = new ArrayList<>();
        for (ProdutoModel produtoModel : ProdutosAdapter.arrayListProdutos) {
            if (produtoModel.isSelecionado()){
                System.out.println(produtoModel.getDescricao());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.busca:
                return true;
            case R.id.add_checked:
                selecionarItensCheckados();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.busca,menu);
        MenuItem menuItem = menu.findItem(R.id.busca);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String buscaStr = s;
                FuncoesCompartilhadas.produtosAdapter.getFilter().filter(buscaStr);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}//public class Produtos