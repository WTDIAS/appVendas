package com.gigalike.appvendas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.ViewHolder> implements Filterable {
    public static ArrayList<ModelProduto> arrayListProdutos;
    //ARRAYLIST ABAIXO É USADO PARA O FILTRO
    private ArrayList<ModelProduto> arrayListProdutosFilter = new ArrayList<>();
    public static Integer totalItens = 0;
    Context context = null;

    //CONSTRUTOR COM PARAMETROS
    public AdapterProdutos(ArrayList<ModelProduto> arrayListProdutos, Context context) {
        this.arrayListProdutos = arrayListProdutos;
        //ARRAYLIST ABAIXO É USADO PARA O FILTRO
        this.arrayListProdutosFilter = arrayListProdutos;
        this.context = context;
    }

    //CONSTRUTOR SEM PARAMETROS
    public AdapterProdutos() {}

    @NonNull
    @Override
    public AdapterProdutos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_linha_produto,parent,false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProdutos.ViewHolder holder, int position) {
        if ((arrayListProdutos != null) && (arrayListProdutos.size() > 0)) {
            ModelProduto produtoItem =  arrayListProdutos.get(position);

            holder.checkBoxSelecionado.setChecked(produtoItem.getSelecionado() ? true : false);

            holder.textViewCodigo.setText(produtoItem.getCodigo());
            holder.textViewDescricao.setText(produtoItem.getDescricao());
            holder.textViewEstoque.setText(produtoItem.getEstoque());
            holder.textViewPreco.setText("R$ "+produtoItem.getPvenda());
            holder.checkBoxSelecionado.setTag(position);
            totalItens = getItemCount();
            holder.checkBoxSelecionado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer posicao = (Integer) holder.checkBoxSelecionado.getTag();
                    if (arrayListProdutos.get(posicao).getSelecionado()) {
                        arrayListProdutos.get(posicao).setSelecionado(false);
                    } else {
                        arrayListProdutos.get(posicao).setSelecionado(true);
                    }
                }
            });
        }//if ((arrayListProdutos != null) && (arrayListProdutos.size() > 0))
    }//public void onBindViewHolder

    @Override
    public int getItemCount() {
        if (arrayListProdutos != null){
            return arrayListProdutos.size();
        }else {
            return 0;
        }

    }

    @Override
    public Filter getFilter() {
        //FILTRO PARA PESQUISA DE PRODUTO POR DESCRICAO
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0){
                    filterResults.values = arrayListProdutosFilter;
                    filterResults.count = arrayListProdutosFilter.size();
                }else {
                    String busca = charSequence.toString().toLowerCase(Locale.ROOT);
                    ArrayList<ModelProduto> arrayListFilter = new ArrayList<>();
                    for (ModelProduto produtoModel : arrayListProdutosFilter){
                        if (produtoModel.getDescricao().toLowerCase(Locale.ROOT).contains(busca) || produtoModel.getCodigo().contains(busca)){
                            arrayListFilter.add(produtoModel);
                        }
                    }
                    filterResults.values = arrayListFilter;
                    filterResults.count = arrayListFilter.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayListProdutos = (ArrayList<ModelProduto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCodigo;
        public TextView textViewEstoque;
        public TextView textViewPreco;
        public TextView textViewDescricao;
        public CheckBox checkBoxSelecionado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCodigo = (TextView) itemView.findViewById(R.id.textViewCodigo);
            textViewEstoque = (TextView) itemView.findViewById(R.id.textViewEstoque);
            textViewPreco= (TextView) itemView.findViewById(R.id.textViewPreco);
            textViewDescricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            checkBoxSelecionado = (CheckBox) itemView.findViewById(R.id.checkBoxSelecionado);
        }
    }


}