package com.example.pedidomobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pedidomobile.R;
import com.example.pedidomobile.controller.PedidoControler;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PedidoControler controler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controler = PedidoControler.getInstance();

    }

    public void novoPedido(View view) {
        controler.limpaControler();

        Intent intent = new Intent(this, PedidoActivity.class);
        startActivity(intent);
    }

    public void listaPedidos(View view) {
        controler.limpaControler();

        Intent intent = new Intent(this, ListaPedidosActivity.class);
        startActivity(intent);
    }
}
