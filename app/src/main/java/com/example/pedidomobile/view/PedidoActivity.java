package com.example.pedidomobile.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedidomobile.R;
import com.example.pedidomobile.adapter.ItemListAdapter;
import com.example.pedidomobile.controller.PedidoControler;
import com.example.pedidomobile.model.Cabecalho;
import com.example.pedidomobile.model.Produto;
import com.example.pedidomobile.model.Resumo;
import com.example.pedidomobile.util.MascaraCampo;
import com.example.pedidomobile.util.MascaraValorMonetario;
import com.example.pedidomobile.util.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PedidoActivity extends AppCompatActivity {

    private LinearLayout llAddItens;
    private Button btAddItens, btData, btGravar, btFinalizar;
    private RecyclerView rvItens;
    private PedidoControler controler;
    private TextInputEditText etNumPedido, etRazaoSocial, etCnpj, etTelefone, etEmail;
    private TextInputEditText etProduto, etQtdItem, etPrecoItem, etTotalItem;
    private TextView tvSituacaoPedido, tvQtdProdutos, tvQtdItens, tvTotalPedido;
    private int ano;
    private int mes;
    private int dia;
    static final int DATE_DIALOG_ID = 1;
    private String situacao;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        llAddItens = (LinearLayout)this.findViewById(R.id.llAddItens);
        rvItens = (RecyclerView)this.findViewById(R.id.rvItens);

        etNumPedido = (TextInputEditText)this.findViewById(R.id.etNumPedido);
        etRazaoSocial = (TextInputEditText)this.findViewById(R.id.etRazaoSocial);
        etCnpj = (TextInputEditText)this.findViewById(R.id.etCnpj);
        etTelefone = (TextInputEditText)this.findViewById(R.id.etTelefone);
        etEmail = (TextInputEditText)this.findViewById(R.id.etEmail);

        etProduto = (TextInputEditText)this.findViewById(R.id.etProduto);
        etQtdItem = (TextInputEditText)this.findViewById(R.id.etQtdItem);
        etPrecoItem = (TextInputEditText)this.findViewById(R.id.etPrecoItem);
        etTotalItem = (TextInputEditText)this.findViewById(R.id.etTotalItem);

        tvSituacaoPedido = (TextView)this.findViewById(R.id.tvSituacaoPedido);
        tvQtdProdutos = (TextView)this.findViewById(R.id.tvQtdProdutos);
        tvQtdItens = (TextView)this.findViewById(R.id.tvQtdItens);
        tvTotalPedido = (TextView)this.findViewById(R.id.tvTotalPedido);

        btAddItens = (Button)this.findViewById(R.id.btAddItens);
        btData = (Button) this.findViewById(R.id.btData);
        btGravar = (Button)this.findViewById(R.id.btGravar);
        btFinalizar = (Button) this.findViewById(R.id.btFinalizar);

        etCnpj.addTextChangedListener(MascaraCampo.insert("##.###.###/####-##",etCnpj));
        etTelefone.addTextChangedListener(MascaraCampo.insert("(##)####-####",etTelefone));

        llAddItens.setVisibility(View.GONE);

        controler = PedidoControler.getInstance();

        if(controler.getPedido().getNumPedido() != null) {
            etNumPedido.setText(String.valueOf(controler.getPedido().getId()));
            situacao = controler.getPedido().getResumo().getSituacao();
            tvSituacaoPedido.setText(getString(R.string.situacao, situacao));
            populaCampos();

        }else {
            etNumPedido.setText(String.valueOf(controler.retornaIdPedido()));
            situacao = getString(R.string.aberto);
            tvSituacaoPedido.setText(getString(R.string.situacao, situacao));

        }

        final Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);
        atualizaBotaoData();

        btData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        etPrecoItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPrecoItem == null) return;
                etPrecoItem.removeTextChangedListener(this);

                BigDecimal parsed = Utils.parseToBigDecimal(editable.toString());
                String formatted = NumberFormat.getCurrencyInstance(Utils.locale).format(parsed);

                String replaceable = String.format("[%s\\s]", Utils.getCurrencySymbol());
                String cleanString = formatted.replaceAll(replaceable, "");

                etPrecoItem.setText(cleanString);
                etPrecoItem.setSelection(cleanString.length());
                etPrecoItem.addTextChangedListener(this);

                etTotalItem.setText(controler.calculaTotal(etQtdItem, etPrecoItem));
            }
        });

        etQtdItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etTotalItem.setText(controler.calculaTotal(etQtdItem, etPrecoItem));
            }
        });


    }

    public void populaCampos(){
        etNumPedido.setText(String.valueOf(controler.getPedido().getNumPedido()));
        etRazaoSocial.setText(controler.getPedido().getRazaoSocial());
        etCnpj.setText(controler.getPedido().getCnpj());
        etTelefone.setText(controler.getPedido().getTelefone());
        etEmail.setText(controler.getPedido().getEmail());

        atualizaListaProdutos();
        ativaDesativaCampos();

    }

    public void ativaDesativaCampos(){
        if(controler.getPedido().getResumo().getSituacao().equals(getString(R.string.finalizado))){
            etRazaoSocial.setEnabled(false);
            etCnpj.setEnabled(false);
            etTelefone.setEnabled(false);
            etEmail.setEnabled(false);
            btAddItens.setVisibility(View.GONE);
            btData.setEnabled(false);
            btGravar.setEnabled(false);
            btFinalizar.setEnabled(false);

        }else{
            etRazaoSocial.setEnabled(true);
            etCnpj.setEnabled(true);
            etTelefone.setEnabled(true);
            etEmail.setEnabled(true);
            btAddItens.setVisibility(View.VISIBLE);
            btData.setEnabled(true);
            btGravar.setEnabled(true);
            btFinalizar.setEnabled(true);
        }
    }

    public void abrirAddItens(View view) {
        llAddItens.setVisibility(View.VISIBLE);
        btAddItens.setVisibility(View.GONE);
        limpaCamposProduto();
        etProduto.requestFocus();
    }

    public void abreAlteracaoItem(Produto prod){
        if(controler.getPosicaoAtualizar() != null){
            etProduto.setText(prod.getProduto());
            etQtdItem.setText(String.valueOf(prod.getQuantidade()));
            etPrecoItem.setText(Utils.formataValor(String.valueOf(prod.getPreco())));
            etTotalItem.setText(Utils.formataValor(String.valueOf(prod.getTotal())));

            llAddItens.setVisibility(View.VISIBLE);
            btAddItens.setVisibility(View.GONE);

            etProduto.requestFocus();
        }
    }

    public void limpaCamposProduto(){
        etProduto.setText("");
        etQtdItem.setText("");
        etPrecoItem.setText("");
        etTotalItem.setText("");
    }

    public void confirmarItem(View view) {

        if(!controler.validaCampoString(etProduto.getText().toString())){
            etProduto.setError("Informe o Produto!");
            etProduto.requestFocus();
            return;
        }
        if(!controler.validaCampoNumerico(etQtdItem.getText().toString())){
            etQtdItem.setError("Informe a quantidade do peoduto!");
            etQtdItem.requestFocus();
            return;
        }
        if(!controler.validaCampoMonetario(etPrecoItem.getText().toString())){
            etPrecoItem.setError("Informe a preço do peoduto!");
            etPrecoItem.requestFocus();
            return;
        }

        Produto prod = new Produto(Long.parseLong(etNumPedido.getText().toString()),
                etProduto.getText().toString(),
                Integer.parseInt(etQtdItem.getText().toString()),
                Double.parseDouble(Utils.formataValorDouble(etPrecoItem.getText().toString())),
                Double.parseDouble(Utils.formataValorDouble(etTotalItem.getText().toString())));

        if(controler.getPosicaoAtualizar() >= 0){
            Produto p = controler.getListaProdutos().get(controler.getPosicaoAtualizar());
            controler.excluiProduto(p);
        }
        controler.addProdutoLista(prod);

        llAddItens.setVisibility(View.GONE);
        btAddItens.setVisibility(View.VISIBLE);

        atualizaListaProdutos();

    }

    private void atualizaBotaoData() {
        btData.setText(new StringBuilder().append(dia).append("/")
                .append(mes+1).append("/")
                .append(ano).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;

            atualizaBotaoData();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);
    }

    public void atualizaListaProdutos(){

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvItens.setLayoutManager(llm);

        ItemListAdapter adapter = new ItemListAdapter(controler.getListaProdutos(), this, this);
        rvItens.setAdapter(adapter);

        atualizaResumo();
    }

    public void atualizaResumo(){
        int qtdItens = 0;
        Double vlrTotal = 0.0;
        for (Produto p: controler.getListaProdutos()) {
            qtdItens += p.getQuantidade();
            vlrTotal += p.getTotal();
        }
        tvQtdProdutos.setText(getString(R.string.qtdprodutos, String.valueOf(controler.getListaProdutos().size())));
        tvQtdItens.setText(getString(R.string.qtditens, String.valueOf(qtdItens)));
        tvTotalPedido.setText(getString(R.string.totalpedido, Utils.formataValor(String.valueOf(vlrTotal))));

        controler.setResumo(new Resumo(Long.parseLong(etNumPedido.getText().toString()), situacao,
                controler.getListaProdutos().size(), qtdItens, vlrTotal));



    }

    public void gravarPedido(View view) {

        controler.setPedido(new Cabecalho(Long.parseLong(etNumPedido.getText().toString()),
                btData.getText().toString(), etRazaoSocial.getText().toString(), etCnpj.getText().toString(),
                etTelefone.getText().toString(), etEmail.getText().toString(), controler.getListaProdutos(), controler.getResumo()));

        String retorno = controler.gravaPedido();
        if(retorno != null)
            abrirDialogErro(retorno);
        else{
            Utils.msgConfirmacao(view, getString(R.string.pedidosalvo));
        }
    }

    public void finalizaPedido(View view) {
        if(!controler.validaCampoString(etRazaoSocial.getText().toString())){
            etRazaoSocial.setError("Informe a Razão Social!");
            etRazaoSocial.requestFocus();
            return;
        }

        if(!controler.validaCampoString(etCnpj.getText().toString())){
            etCnpj.setError("Informe o Cnpj!");
            etCnpj.requestFocus();
            return;
        }

        if(!controler.validaCampoString(etTelefone.getText().toString())){
            etTelefone.setError("Informe o Telefone!");
            etTelefone.requestFocus();
            return;
        }

        if(!controler.validaCampoString(etEmail.getText().toString())){
            etEmail.setError("Informe o Email!");
            etEmail.requestFocus();
            return;
        }

        if(!Utils.isCnpjValido(etCnpj.getText().toString())){
            etCnpj.setError("Cnpj inválido!");
            etCnpj.requestFocus();
            return;
        }

        if(!Utils.validaEmail(etEmail.getText().toString())){
            etEmail.setError("Email inválido!");
            etEmail.requestFocus();
            return;
        }

        if(controler.getListaProdutos().size() <= 0){
            abrirDialogErro(getString(R.string.validaitens));
        }else{

            Resumo resumo = controler.getResumo();
            resumo.setSituacao(getString(R.string.finalizado));
            controler.setResumo(resumo);

            controler.setPedido(new Cabecalho(Long.parseLong(etNumPedido.getText().toString()),
                    btData.getText().toString(), etRazaoSocial.getText().toString(), etCnpj.getText().toString(),
                    etTelefone.getText().toString(), etEmail.getText().toString(), controler.getListaProdutos(), controler.getResumo()));

            String retorno = controler.gravaPedido();
            if(retorno != null)
                abrirDialogErro(retorno);
            else{
                Utils.msgConfirmacao(view, getString(R.string.pedidofinalizado));

                Intent intent = new Intent(this, ListaPedidosActivity.class);
                startActivity(intent);
            }
        }


    }

    public void abrirDialogErro(String mensagem){

        View v = LayoutInflater.from(this).inflate(R.layout.dialog_mensagem,null);

        TextView tvMensagem = (TextView)v.findViewById(R.id.tvMensagem);
        Button btConfirma = (Button)v.findViewById(R.id.btConfirma);
        Button btCancela = (Button)v.findViewById(R.id.btCancela);
        Button btOk = (Button)v.findViewById(R.id.btOk);
        TextView tvTitulo = (TextView)v.findViewById(R.id.tvTitulo);

        tvTitulo.setText(getString(R.string.erro));
        tvMensagem.setText(mensagem);
        btConfirma.setVisibility(View.GONE);
        btCancela.setVisibility(View.GONE);
        btOk.setVisibility(View.VISIBLE);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerta.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        builder.setCancelable(false);
        alerta = builder.create();
        alerta.show();

    }


    public void voltar(View view) {
        this.finish();
    }

    @Override
    public void onBackPressed() {

    }
}
