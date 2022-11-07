package com.gigalike.appvendas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ActivityClientes extends AppCompatActivity {
    FuncoesArmazenamentoInterno funcoesArmazenamentoInterno = new FuncoesArmazenamentoInterno();
    boolean statusCampos = true;
    ModelCidade cidadeSelecionada  = new ModelCidade();;

    EditText editTextNome = null;
    EditText editTextCpfCnpj = null;
    EditText editTextTelefone = null;
    EditText editTextLogradouro = null;
    EditText editTextNumero = null;
    EditText editTextBairro = null;
    EditText editTextComplemento = null;
    EditText textViewCidade = null;
    EditText editTextCep = null;
    EditText textViewUf = null;
    EditText editTextEmail = null;
    EditText editTextObservacao = null;

    Button btnSalvar = null;
    Button btnEditar = null;
    Button btnConsultar = null;
    Button btnCidade = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        //BAIXO ESTOU COLOCANDO MEU MENU NA MINHA TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClientes);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_clientes_activity);

        editTextNome = (EditText)findViewById(R.id.editTextNome);
        editTextCpfCnpj = (EditText)findViewById(R.id.editTextCpfCnpj);
        editTextTelefone = (EditText)findViewById(R.id.editTextTelefone);
        editTextLogradouro = (EditText)findViewById(R.id.editTextLogradouro);
        editTextNumero = (EditText)findViewById(R.id.editTextNumero);
        editTextBairro = (EditText)findViewById(R.id.editTextBairro);
        editTextComplemento = (EditText)findViewById(R.id.editTextComplemento);
        textViewCidade = (EditText) findViewById(R.id.textViewCidade);
        editTextCep = (EditText)findViewById(R.id.editTextCep);
        textViewUf = (EditText)findViewById(R.id.editTextUf);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextObservacao = (EditText)findViewById(R.id.editTextObservacao);

        btnCidade = findViewById(R.id.btnCidade);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnEditar = findViewById(R.id.btnEditar);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ativaInativaCampos();
                //setFocusable = false para não permitir alteração do CPF
                editTextCpfCnpj.setEnabled(false);
            }
        });


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDadosClienteDaTelaNoArmazenamentoInterno();
                Intent intent = new Intent(ActivityClientes.this, ActivityCidades.class);
                startActivity(intent);
            }
        });

        //Abaixo estou pegando os dados da cidade após a selecão da mesma na activity cidade
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            cidadeSelecionada.setNomeCidade((String) bundle.get("cidadeExtra"));
            cidadeSelecionada.setCodigoIBGE((String) bundle.get("codIbgeExtra"));
            cidadeSelecionada.setUnidadeFederativa((String) bundle.get("ufExtra"));
            textViewCidade.setText(cidadeSelecionada.getNomeCidade());
            textViewUf.setText(cidadeSelecionada.getUnidadeFederativa());
            ModelCliente clienteModel = funcoesArmazenamentoInterno.leituraDadosClienteDoArmazenamentoInterno(ActivityClientes.this);
            reesibirDadosDoClienteNaTela(clienteModel);
        }

    }//protected void onCreate


    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> minhaClasse) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, minhaClasse));
        }
        return arrayList;
    }



    //Função abaixo ativa e desativa os campos para edição, exceto CPF/CNPJ
    public void ativaInativaCampos(){
        if (statusCampos){
            editTextNome.setEnabled(false);
            editTextCpfCnpj.setEnabled(false);
            editTextTelefone.setEnabled(false);
            editTextLogradouro.setEnabled(false);
            editTextNumero.setEnabled(false);
            editTextBairro.setEnabled(false);
            editTextComplemento.setEnabled(false);
            editTextCep.setEnabled(false);
            editTextEmail.setEnabled(false);
            editTextObservacao.setEnabled(false);
            statusCampos = false;
        }else {
            editTextNome.setEnabled(true);
            editTextCpfCnpj.setEnabled(true);
            editTextTelefone.setEnabled(true);
            editTextLogradouro.setEnabled(true);
            editTextNumero.setEnabled(true);
            editTextBairro.setEnabled(true);
            editTextComplemento.setEnabled(true);
            editTextCep.setEnabled(true);
            editTextEmail.setEnabled(true);
            editTextObservacao.setEnabled(true);
            statusCampos = true;
        }
    }

    //Ao sair da tela para selecionar a cidade esta função salva os dados ja preenchidos no
    // armazenamento interno para não perde-las para em seguida reexibilas
    public void salvarDadosClienteDaTelaNoArmazenamentoInterno(){
        ModelCliente clienteModel = new ModelCliente();

        clienteModel.setNome(editTextNome.getText().toString());
        clienteModel.setCpfCnpj(editTextCpfCnpj.getText().toString());
        clienteModel.setTelefone(editTextTelefone.getText().toString());
        clienteModel.setLogradouro(editTextLogradouro.getText().toString());
        clienteModel.setNumero(editTextNumero.getText().toString());
        clienteModel.setBairro(editTextBairro.getText().toString());
        clienteModel.setComplemento(editTextComplemento.getText().toString());
        clienteModel.setCep(editTextCep.getText().toString());
        clienteModel.setEmail(editTextEmail.getText().toString());
        clienteModel.setObservacao(editTextObservacao.getText().toString());

        //Os campos abaixo ao invés de pegar da tela estou pegando do objeto cidadeSelecionada que
        // é alimentado após a selecão da cidade na activity cidade.
        clienteModel.setNomeCidade(cidadeSelecionada.getNomeCidade());
        clienteModel.setUnidadeFederativa(cidadeSelecionada.getUnidadeFederativa());
        clienteModel.setCodigoIBGE(cidadeSelecionada.getCodigoIBGE());

        funcoesArmazenamentoInterno.salvarDadosClienteNoArmazenamentoInterno(clienteModel, ActivityClientes.this);

    }


    //Após sair da tela de clientes para selecionar a cidade salvei os dados no armazenamento
    // interno, após buscar os dados novamente em um clienteModel passo para função abaixo que reexibe.
    public void reesibirDadosDoClienteNaTela(ModelCliente clienteModel){
        editTextNome.setText(clienteModel.getNome());
        editTextCpfCnpj.setText(clienteModel.getCpfCnpj());
        editTextTelefone.setText(clienteModel.getTelefone());
        editTextLogradouro.setText(clienteModel.getLogradouro());
        editTextNumero.setText(clienteModel.getNumero());
        editTextBairro.setText(clienteModel.getBairro());
        editTextComplemento.setText(clienteModel.getComplemento());
        editTextCep.setText(clienteModel.getCep());
        editTextEmail.setText(clienteModel.getEmail());
        editTextObservacao.setText(clienteModel.getObservacao());
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientes_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }





}