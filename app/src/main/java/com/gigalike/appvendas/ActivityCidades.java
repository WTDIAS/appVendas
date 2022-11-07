package com.gigalike.appvendas;

import static com.gigalike.appvendas.ActivityClientes.jsonToArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityCidades extends AppCompatActivity {
    private FuncoesArmazenamentoInterno funcoesArmazenamentoInterno = new FuncoesArmazenamentoInterno();
    private CidadesSqlite cidadesSqlite = new CidadesSqlite(ActivityCidades.this);
    SearchView searchView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidades);
        searchView = (SearchView)findViewById(R.id.searchViewPesquisaCidade);


        //Abaixo estou fazendo uma consulta por uma cidade específica apenas
        // para saber se já existe a tabela e as colunas criadas no SQLite
        List<ModelCidade> listTestaConexão = cidadesSqlite.buscaCidades("ARIQUEMES");
        if (!(listTestaConexão.size() > 0)){
            //Abaixo leitura do arquivo cidadesJaon.json para String
            String jsonStringCidades = funcoesArmazenamentoInterno.lerCidadesJson(ActivityCidades.this, "cidadesJson.json");
            //Abaixo tranferindo os dados da String para um List de objetos CidadeModel
            List<ModelCidade> cidadesList = jsonToArrayList(jsonStringCidades, ModelCidade.class);
            //Abaixo transferindo a Lista de objetos para salvar no SQLite
            cidadesSqlite.inserirCidades(cidadesList);
        }



        //Código abaixo é para possibilitar o click em qualquer lugar do searchview e não somente no ícone
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            ArrayList<ModelCidade> modelCidadeList = new ArrayList<>();
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    //Abaixo: Se foi digitado algo para pesquisar
                    if (!query.isEmpty()){
                        //Abaixo estou fazendo a consulta de cidade no SQLite pela string digitada pelo usuário
                        modelCidadeList = cidadesSqlite.buscaCidades(query);
                        if (modelCidadeList.size() > 0){
                            FuncoesCompartilhadas.exibirCidadesNaTela(modelCidadeList, ActivityCidades.this);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        //BAIXO ESTOU COLOCANDO MEU MENU NA MINHA TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCidades);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_cidades_activity);



    }//protected void onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cidades_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



}//public class CidadesActivity