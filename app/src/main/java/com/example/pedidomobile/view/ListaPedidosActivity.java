package com.example.pedidomobile.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pedidomobile.R;
import com.example.pedidomobile.adapter.ItemListAdapter;
import com.example.pedidomobile.adapter.PedidoListAdapter;
import com.example.pedidomobile.controller.PedidoControler;

public class ListaPedidosActivity extends AppCompatActivity {

    private RecyclerView rvPedidos;
    private PedidoControler controler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        controler = PedidoControler.getInstance();

        atualizaListaPedidos();
    }

    public void atualizaListaPedidos(){
        rvPedidos = (RecyclerView)this.findViewById(R.id.rvPedidos);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvPedidos.setLayoutManager(llm);

        PedidoListAdapter adapter = new PedidoListAdapter(controler.retornaPedidos(), this);
        rvPedidos.setAdapter(adapter);

    }
}