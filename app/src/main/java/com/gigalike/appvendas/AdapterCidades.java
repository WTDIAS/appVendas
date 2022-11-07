package com.gigalike.appvendas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class AdapterCidades extends RecyclerView.Adapter<AdapterCidades.viewHolder> {
    public static ArrayList<ModelCidade> arrayListCidades = new ArrayList<>();
    //ARRAYLIST ABAIXO É USADO PARA O FILTRO
    private ArrayList<ModelCidade> arrayListCidadesFilter = new ArrayList<>();
    public static Integer totalItens = 0;
    Context context = null;

    //CONSTRUTOR COM PARAMETROS
    public AdapterCidades(ArrayList<ModelCidade> arrayListCidades, Context context) {
        this.arrayListCidades = arrayListCidades;
        this.arrayListCidadesFilter = arrayListCidades;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterCidades.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_linha_cidade,parent,false
        );
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCidades.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        if ((arrayListCidades != null) && (arrayListCidades.size() > 0)) {
            ModelCidade modelCidade = arrayListCidades.get(position);
            holder.editTextCidade.setText(modelCidade.getNomeCidade());
            holder.editTextUf.setText(modelCidade.getUnidadeFederativa());


            //Abaixo ao clicar na cidade pego as informações do item clicado e abro activity cliente
            // ja preenchendo os campos cidade e uf
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentCliente = new Intent(context, ActivityClientes.class);
                    intentCliente.putExtra("cidadeExtra",arrayListCidades.get(position).getNomeCidade());
                    intentCliente.putExtra("ufExtra",arrayListCidades.get(position).getUnidadeFederativa());
                    intentCliente.putExtra("codIbgeExtra",arrayListCidades.get(position).getCodigoIBGE());
                    context.startActivity(intentCliente);

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (arrayListCidades != null){
            return arrayListCidades.size();
        }else {
            return 0;
        }
    }

    public class viewHolder  extends RecyclerView.ViewHolder {
        public TextView editTextCidade = null;
        public TextView editTextUf = null;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            editTextCidade = (TextView)itemView.findViewById(R.id.linhaCidadeEditTextCidade);
            editTextUf = (TextView)itemView.findViewById(R.id.linhaCidadeEditTextUf);
        }
    }
}
