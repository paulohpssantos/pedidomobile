package com.example.pedidomobile.controller;


import android.widget.EditText;

import com.example.pedidomobile.model.Cabecalho;
import com.example.pedidomobile.model.Produto;
import com.example.pedidomobile.model.Resumo;
import com.example.pedidomobile.util.Utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public final class PedidoControler {

    private static PedidoControler instancia;
    private static List<Produto>listaProdutos;
    private static Cabecalho pedido;
    private static Resumo resumo;
    private Integer posicaoAtualizar;

    public static PedidoControler getInstance(){
        if (instancia == null){
            instancia = new PedidoControler();
        }
        return instancia;
    }

    public static void limpaControler(){
        instancia = new PedidoControler();
    }

    private PedidoControler() {
        pedido = new Cabecalho();
        listaProdutos = new ArrayList<>();
        resumo = new Resumo();
        posicaoAtualizar = -1;
    }

    public boolean validaCampoString(String conteudo){
        return conteudo != null && !conteudo.isEmpty();
    }

    public boolean validaCampoNumerico(String conteudo){
        if(conteudo != null && !conteudo.isEmpty()){
            return Double.parseDouble(conteudo) > 0;
        }else
            return false;
    }

    public boolean validaCampoMonetario(String conteudo){
        if(conteudo != null && !conteudo.isEmpty()){
            return Double.parseDouble(Utils.formataValorDouble(conteudo)) > 0;
        }else
            return false;
    }

    public String gravaPedido(){
        try{
            pedido.save();

            if(pedido.getProdutos().size()>0){
                for (Produto p: pedido.getProdutos()) {
                    p.save();
                }
            }

            if(pedido.getResumo() != null){
                Resumo resumo = pedido.getResumo();
                resumo.save();
            }

        }catch (Exception ex){
            return "Erro ao gravar pedido: ERRO: "+ex.getMessage();
        }
        return null;
    }


    public List<Produto> retornaProdutos(long id){
        return Produto.find(Produto.class, "id_pedido = ?", new String[]{String.valueOf(id)}, null, null, null );
    }

    public Resumo retornaResumo(long id){
        return Resumo.find(Resumo.class, "id_pedido = ?", new String[]{String.valueOf(id)}, null, null, null).get(0);
    }

    public void excluiProduto(Produto produto){
        this.listaProdutos.remove(produto);

        int qtdItens = 0;
        Double vlrTotal = 0.0;
        for (Produto p: this.listaProdutos) {
            qtdItens += p.getQuantidade();
            vlrTotal += p.getTotal();
        }
        this.resumo.setQtdItem(qtdItens);
        this.resumo.setQtdProduto(this.listaProdutos.size());
        this.resumo.setTotal(vlrTotal);

        if(produto.getId() != null){
            produto.delete();
            this.resumo.save();
        }


    }

    public List<Cabecalho> retornaPedidos(){
        List<Cabecalho> listaCab = new ArrayList<>();
        List<Cabecalho> listaRetorno = new ArrayList<>();
        List<Produto> listaProdutos = new ArrayList<>();
        listaCab = Cabecalho.find(Cabecalho.class, null, null, null, "num_pedido desc", null );

        if(listaCab.size() > 0){
            for (Cabecalho cab : listaCab) {
                listaProdutos = retornaProdutos(cab.getNumPedido());
                Resumo res = new Resumo();
                res = retornaResumo(cab.getNumPedido());
                cab.setProdutos(listaProdutos);
                cab.setResumo(res);
                listaRetorno.add(cab);
            }
        }

        return listaRetorno;
    }

    public String calculaTotal(EditText etQtdItem, EditText etPrecoItem){
        int quantidade;
        String preco;
        if(etQtdItem.getText().toString().equals(""))
            quantidade = 0;
        else
            quantidade = Integer.parseInt(etQtdItem.getText().toString());

        if(etPrecoItem.getText().toString().equals(""))
            preco = "0.0";
        else
            preco = Utils.formataValorDouble(etPrecoItem.getText().toString());

        BigDecimal parsed = Utils.parseToBigDecimal(preco);

        BigDecimal total = parsed.multiply(new BigDecimal(quantidade));
        String formatted = NumberFormat.getCurrencyInstance(Utils.locale).format(total);

        String replaceable = String.format("[%s\\s]", Utils.getCurrencySymbol());
        String cleanString = formatted.replaceAll(replaceable, "");

        return cleanString;

    }
    public long retornaIdPedido(){
        return Cabecalho.count(Cabecalho.class,null,null) + 1;
    }

    public List<Produto> getListaProdutos() {
        return this.listaProdutos;
    }

    public void setListaProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public void addProdutoLista(Produto produto){
        this.listaProdutos.add(produto);
    }

    public static Cabecalho getPedido() {
        return pedido;
    }

    public void setPedido(Cabecalho pedido) {
        PedidoControler.pedido = pedido;
        this.setListaProdutos(pedido.getProdutos());
        this.setResumo(pedido.getResumo());
    }

    public static Resumo getResumo() {
        return resumo;
    }

    public static void setResumo(Resumo resumo) {
        PedidoControler.resumo = resumo;
        PedidoControler.pedido.setResumo(resumo);
    }

    public Integer getPosicaoAtualizar() {
        return posicaoAtualizar;
    }

    public void setPosicaoAtualizar(Integer posicaoAtualizar) {
        this.posicaoAtualizar = posicaoAtualizar;
    }
}
