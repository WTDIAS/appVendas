package com.gigalike.appvendas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import java.util.ArrayList;

//**********************************************************************
//VIDEO IMPLEMENTACAO FILTRO NO RECYRCLERVIEW                          *
//https://www.youtube.com/watch?v=MWlxFccYit8&ab_channel=larntech      *
//VIDEO BOTÃO VOLTAR NA APPBAR                                         *
//https://www.youtube.com/watch?v=JkVdP-e9BCo&ab_channel=CodinginFlow  *
//**********************************************************************


public class ActivityProdutos extends AppCompatActivity {
    //public Activity activity;
    private ProdutosSqlite produtosSqlite = new ProdutosSqlite(ActivityProdutos.this);
    private FuncoesArmazenamentoInterno funcoesArmazenamentoInterno = new FuncoesArmazenamentoInterno();
    LerApiProdutos lerApiProdutos = new LerApiProdutos(ActivityProdutos.this);
    private ArrayList<ModelProduto> arrayListModelProduto = new ArrayList<>();
    private ProgressBar myProgressBar;
    private AdapterProdutos adapterProdutos = new AdapterProdutos();
    private AlertDialog.Builder builder = null;
    //sufixoTabela é o numero colocado ao fim do nome de cada tabela
    //no banco de dados para identificar de qual cliente é a tabela
    String sufixoTabela = "1";
    String url = "https://app-vendas-v3.herokuapp.com/buscarTodosProdutos/"+sufixoTabela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        //BAIXO ESTOU COLOCANDO MEU MENU NA MINHA TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_produtos_activity);

        myProgressBar = findViewById(R.id.myProgressBar);

        exibeProdutos();


    }// protected void onCreate

    private void exibeProdutos(){
        //A PRIMEIRA VEZ QUE É ABERTO O APP NO DIA ELE BUSCA O CADASTRO DE PRODUTOS NA API, NAS PROXIMAS ELE BUSCA DO SQLite
        //O IF ELSE ABAIXO VERIFICA SE A DATA DA ATUALIZAÇÃO SALVA NO ARMAZENAMENTO INTERNO É IGUAL A DATA ATUAL
        //SE JA EXISTE A DATA DA ULTIMA ATUALIZAÇÃO VERIFICO SE É IGUAL A DATA ATUAL
        String diaMesATual = FuncoesCompartilhadas.dataATual().substring(0,5);
        String ultimaAtualizacao = funcoesArmazenamentoInterno.sharPrefLeitura(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(), ActivityProdutos.this);
        String diaMesUltimaAtualizacao = "";
        if (!ultimaAtualizacao.equals("")){
            diaMesUltimaAtualizacao = ultimaAtualizacao.substring(0,5);
        }
        //SE DATA DA ULTIMA ATUALIZAÇÃO SALVA NO ARMAZENAMENTO INTERNO FOR IGUAL DATA ATUAL ENTÃO CARREGO OS PRODUTOS DO SQLite
        if (diaMesUltimaAtualizacao.equals(diaMesATual)){
            arrayListModelProduto = produtosSqlite.buscaProdutos();

            if (arrayListModelProduto.size() > 0) {
                FuncoesCompartilhadas.exibirProdutosNaTela(arrayListModelProduto, ActivityProdutos.this);
            }else {
                lerApiProdutos.lerTodosProdutos(url, new MinhasInterfaces.QuandoApiRetornarTodosrProdutos() {
                    //ACIMA É UTILIZADO COMO PARÂMETRO UMA INTERFACE, DESTA FORMA ELA SÓ É EXECUTADA QUANDO OUVER O RETORNO
                    //DO MÉTODO QUE A IMPLEMENTA
                    @Override
                    public void retornoApiTodosProdutos(ArrayList<ModelProduto> arrayListProdutos) {
                        FuncoesCompartilhadas.exibirProdutosNaTela(arrayListProdutos, ActivityProdutos.this);
                        //CARREGANDO OS PRODUTOS DO SQLite
                        produtosSqlite.inserirProdutos(arrayListProdutos);
                        //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO NO ARQUIVO SHARPREF DO ARMAZENAMENTO INTERNO
                        funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(), FuncoesCompartilhadas.dataATual(), ActivityProdutos.this);
                    }
                });
            }
        }else{
            //SE A DATA DA ULTIMA ATUALIZACAO NÃO FOR IGUAL A DATA ATUAL CARREGO DA API
            lerApiProdutos.lerTodosProdutos(url, new MinhasInterfaces.QuandoApiRetornarTodosrProdutos() {
                //ACIMA É UTILIZADO COMO PARÂMETRO UMA INTERFACE, DESTA FORMA ELA SÓ É EXECUTADA QUANDO OUVER O RETORNO
                //DO MÉTODO QUE A IMPLEMENTA
                @Override
                public void retornoApiTodosProdutos(ArrayList<ModelProduto> arrayListProdutos) {
                    //SE A API RETORNAR DADOS, ANTES DE CARREGA-LOS FAÇO EXCLUSÃO DO QUE JÁ EXISTE NO ARMAZENAMENTO INTERNO
                    if (arrayListProdutos.size() > 0){
                        produtosSqlite.dropTableProdutos();
                    }
                    FuncoesCompartilhadas.exibirProdutosNaTela(arrayListProdutos, ActivityProdutos.this);
                    //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO NO ARQUIVO SHARPREF DO ARMAZENAMENTO INTERNO
                    produtosSqlite.inserirProdutos(arrayListProdutos);
                    funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(), FuncoesCompartilhadas.dataATual(), ActivityProdutos.this);
                }
            });

        }
    }//private void exibeProdutos()



    private void selecionarItensCheckados() {
        for (ModelProduto modelProduto : AdapterProdutos.arrayListProdutos) {
            if (modelProduto.getSelecionado()){
                System.out.println(modelProduto.getDescricao());
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
            case R.id.btnAtualizar:
                exibirAlertDialogSimNão("Atualizar o cadastro de produtos com a núvem? (Será necessário conexão com a internet.)");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_produtos_activity,menu);
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
                FuncoesCompartilhadas.adapterProdutos.getFilter().filter(buscaStr);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    //Função executada ao clicar no botão atualizar pois o app faz a leitura da api apenas uma vez ao dia
    // esta funcao serve para se por acaso o usuário desejar forçar esta atualização
    private void atualizarProdutosPelaApi(){
        //LIMPANDO OS DADOS DO ARRAY ADAPTER
        adapterProdutos.arrayListProdutos.clear();
        //NOTIFICANDO QUE HOUVE ALTERAÇÕES
        adapterProdutos.notifyDataSetChanged();
        //MOSTRANDO A PROGRESSBAR
        myProgressBar.setVisibility(View.VISIBLE);
        lerApiProdutos.lerTodosProdutos(url, new MinhasInterfaces.QuandoApiRetornarTodosrProdutos() {
            //ACIMA É UTILIZADO COMO PARÂMETRO UMA INTERFACE, DESTA FORMA ELA SÓ É EXECUTADA QUANDO OUVER O RETORNO
            //DO MÉTODO QUE A IMPLEMENTA
            @Override
            public void retornoApiTodosProdutos(ArrayList<ModelProduto> arrayListProdutos) {
                //SE A API RETORNAR DADOS, ANTES DE CARREGA-LOS FAÇO EXCLUSÃO DO QUE JÁ EXISTE NO ARMAZENAMENTO INTERNO
                if (arrayListProdutos.size() > 0){
                    produtosSqlite.dropTableProdutos();
                }
                produtosSqlite.inserirProdutos(arrayListProdutos);
                FuncoesCompartilhadas.exibirProdutosNaTela(arrayListProdutos, ActivityProdutos.this);
                //ATUALIZANDO DATA DA ULTIMA ATUALIZACAO NO ARQUIVO SHARPREF DO ARMAZENAMENTO INTERNO
                funcoesArmazenamentoInterno.sharedPrefSalvar(funcoesArmazenamentoInterno.getNomeSharPrefUltimaAtualizacao(), FuncoesCompartilhadas.dataATual(), ActivityProdutos.this);
            }
        });
    }



    private void exibirAlertDialogSimNão(String mensagemSerExibida) {
        try {
            builder = new AlertDialog.Builder(ActivityProdutos.this);
            builder.setMessage(mensagemSerExibida);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    atualizarProdutosPelaApi();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}//public class Produtos