package com.example.pedidomobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidomobile.R;
import com.example.pedidomobile.controller.PedidoControler;
import com.example.pedidomobile.model.Produto;
import com.example.pedidomobile.util.Utils;
import com.example.pedidomobile.view.PedidoActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ListViewHolder> {

    public static List<Produto> listaProdutos;
    private Context context;
    public static PedidoControler controler;
    public static ItemListAdapter itemListAdapter;
    public static PedidoActivity activity;



    public ItemListAdapter(List<Produto> lista, Context context, PedidoActivity activity) {
        this.listaProdutos = lista;
        this.context = context;
        this.controler = PedidoControler.getInstance();
        this.itemListAdapter = this;
        this.activity = activity;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextInputEditText etProduto;
        TextInputEditText etQuantidade;
        TextInputEditText etvalor;
        TextInputEditText etTotal;
        ImageButton btEditaItem;
        ImageButton btExcluiItem;
        int position;
        private AlertDialog alerta;

        public ListViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;

            etProduto = (TextInputEditText)itemView.findViewById(R.id.etProduto);
            etQuantidade = (TextInputEditText)itemView.findViewById(R.id.etQuantidade);
            etvalor = (TextInputEditText)itemView.findViewById(R.id.etvalor);
            etTotal = (TextInputEditText)itemView.findViewById(R.id.etTotal);

            btEditaItem = (ImageButton)itemView.findViewById(R.id.btEditaItem);
            btExcluiItem = (ImageButton)itemView.findViewById(R.id.btExcluiItem);

            if(controler.getPedido().getResumo().getSituacao().equals(context.getString(R.string.aberto))){
                btEditaItem.setVisibility(View.VISIBLE);
                btExcluiItem.setVisibility(View.VISIBLE);
            }else{
                btEditaItem.setVisibility(View.GONE);
                btExcluiItem.setVisibility(View.GONE);
            }

            btEditaItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controler.setPosicaoAtualizar(position);
                    activity.abreAlteracaoItem(listaProdutos.get(position));
                }
            });

            btExcluiItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirDialogExclusao(position);
                }
            });
        }

        public void abrirDialogExclusao(int position){

            View v = LayoutInflater.from(context).inflate(R.layout.dialog_mensagem,null);

            TextView tvMensagem = (TextView)v.findViewById(R.id.tvMensagem);
            Button btConfirma = (Button)v.findViewById(R.id.btConfirma);
            Button btCancela = (Button)v.findViewById(R.id.btCancela);
            Button btOk = (Button)v.findViewById(R.id.btOk);
            TextView tvTitulo = (TextView)v.findViewById(R.id.tvTitulo);

            tvTitulo.setText(context.getString(R.string.atencao));
            tvMensagem.setText(context.getString(R.string.excluirprod));
            btConfirma.setVisibility(View.VISIBLE);
            btCancela.setVisibility(View.VISIBLE);
            btOk.setVisibility(View.GONE);

            btCancela.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerta.dismiss();
                }
            });

            btConfirma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removerItem(position);
                    alerta.dismiss();
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(v);
            builder.setCancelable(false);
            alerta = builder.create();
            alerta.show();

        }

        public void removerItem(int position){
            Produto prod = listaProdutos.get(position);
            controler.excluiProduto(prod);
            //listaProdutos.remove(position);

            activity.atualizaListaProdutos();

        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_produto, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view, context);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);

        holder.etProduto.setText(produto.getProduto());
        holder.etQuantidade.setText(String.valueOf(produto.getQuantidade()));
        holder.etvalor.setText("R$"+Utils.formataValorString(String.valueOf(produto.getPreco())));
        holder.etTotal.setText("R$"+Utils.formataValorString(String.valueOf(produto.getTotal())));
        holder.position = position;


    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }






}
