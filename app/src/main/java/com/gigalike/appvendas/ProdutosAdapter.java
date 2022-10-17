package com.gigalike.appvendas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> implements Filterable {
    public static ArrayList<ProdutoModel> arrayListProdutos;
    //ARRAYLIST ABAIXO É USADO PARA O FILTRO
    private ArrayList<ProdutoModel> arrayListProdutosFilter = new ArrayList<>();
    public static Integer totalItens = 0;
    Context context = null;

    public ProdutosAdapter(ArrayList<ProdutoModel> arrayListProdutos, Context context) {
        this.arrayListProdutos = arrayListProdutos;
        //ARRAYLIST ABAIXO É USADO PARA O FILTRO
        this.arrayListProdutosFilter = arrayListProdutos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_produtos,parent,false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosAdapter.ViewHolder holder, int position) {
        if ((arrayListProdutos != null) && (arrayListProdutos.size() > 0)) {
            ProdutoModel itemLeituraModel =  arrayListProdutos.get(position);

            holder.checkBoxSelecionado.setChecked(itemLeituraModel.isSelecionado());

            holder.textViewCodigo.setText(itemLeituraModel.getCodigo());
            holder.textViewDescricao.setText(itemLeituraModel.getDescricao());
            holder.textViewEstoque.setText(itemLeituraModel.getEstoque());
            holder.textViewPreco.setText(itemLeituraModel.getPvenda());
            holder.checkBoxSelecionado.setTag(position);
            totalItens = getItemCount();
            holder.checkBoxSelecionado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer posicao = (Integer) holder.checkBoxSelecionado.getTag();
                    if (arrayListProdutos.get(posicao).isSelecionado()) {
                        arrayListProdutos.get(posicao).setSelecionado(false);
                    } else {
                        arrayListProdutos.get(posicao).setSelecionado(true);
                    }
                }
            });
        }//if
    }

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
                    ArrayList<ProdutoModel> arrayListFilter = new ArrayList<>();
                    for (ProdutoModel produtoModel : arrayListProdutosFilter){
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
                arrayListProdutos = (ArrayList<ProdutoModel>) filterResults.values;
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