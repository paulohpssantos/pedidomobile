package com.example.pedidomobile.model;


import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Produto extends SugarRecord {

    private Long idPedido;
    private String produto;
    private int quantidade;
    private Double preco;
    private Double total;

    public Produto() {
    }

    public Produto(Long idPedido, String produto, int quantidade, Double preco, Double total) {
        this.idPedido = idPedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
