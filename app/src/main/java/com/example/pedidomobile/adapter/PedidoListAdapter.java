package com.example.pedidomobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidomobile.R;
import com.example.pedidomobile.controller.PedidoControler;
import com.example.pedidomobile.model.Cabecalho;
import com.example.pedidomobile.util.Utils;
import com.example.pedidomobile.view.PedidoActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class PedidoListAdapter extends RecyclerView.Adapter<PedidoListAdapter.PedidoViewHolder> {

    private List<Cabecalho> listaPedidos;
    private Context context;
    PedidoControler controler;

    public PedidoListAdapter(List<Cabecalho> listaPedidos, Context context) {
        this.listaPedidos = listaPedidos;
        this.context = context;
        controler = PedidoControler.getInstance();
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pedido, parent, false);
        PedidoViewHolder viewHolder = new PedidoViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Cabecalho cabecalho = listaPedidos.get(position);

        holder.etNumPedido.setText(String.valueOf(cabecalho.getNumPedido()));
        holder.etDatPedido.setText(cabecalho.getData());
        holder.etRazaoSocial.setText(cabecalho.getRazaoSocial());
        holder.etCnpj.setText(cabecalho.getCnpj());

        if(cabecalho.getResumo() != null){
            holder.etDescSituacao.setText(cabecalho.getResumo().getSituacao());
            holder.etQtdProd.setText(String.valueOf(cabecalho.getResumo().getQtdProduto()));
            holder.etQtdItens.setText(String.valueOf(cabecalho.getResumo().getQtdItem()));
            holder.etTotal.setText(Utils.formataValor(String.valueOf(cabecalho.getResumo().getTotal())));
        }

        holder.pedidoSelecionado = cabecalho;
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder{

        Context context;
        TextInputEditText etNumPedido,etDatPedido, etRazaoSocial, etCnpj, etDescSituacao;
        TextInputEditText etQtdProd, etQtdItens, etTotal;
        ImageButton btEditaItem;
        Cabecalho pedidoSelecionado;

        public PedidoViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;

            etNumPedido = (TextInputEditText)itemView.findViewById(R.id.etNumPedido);
            etDatPedido = (TextInputEditText)itemView.findViewById(R.id.etDatPedido);
            etRazaoSocial = (TextInputEditText)itemView.findViewById(R.id.etRazaoSocial);
            etCnpj = (TextInputEditText)itemView.findViewById(R.id.etCnpj);
            etDescSituacao = (TextInputEditText)itemView.findViewById(R.id.etDescSituacao);
            etQtdProd = (TextInputEditText)itemView.findViewById(R.id.etQtdProd);
            etQtdItens = (TextInputEditText)itemView.findViewById(R.id.etQtdItens);
            etTotal = (TextInputEditText)itemView.findViewById(R.id.etTotal);

            btEditaItem = (ImageButton)itemView.findViewById(R.id.btEditaItem);

            btEditaItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controler.setPedido(pedidoSelecionado);
                    Intent intent = new Intent(context, PedidoActivity.class);
                    context.startActivity(intent);
                }
            });


        }
    }
}
