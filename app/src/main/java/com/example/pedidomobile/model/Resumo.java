package com.example.pedidomobile.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.math.BigDecimal;

public class Resumo extends SugarRecord {

    @Unique
    private Long idPedido;
    private String situacao;
    private int qtdProduto;
    private int qtdItem;
    private Double total;

    public Resumo() {
    }

    public Resumo(Long idPedido, String situacao, int qtdProduto, int qtdItem, Double total) {
        this.idPedido = idPedido;
        this.situacao = situacao;
        this.qtdProduto = qtdProduto;
        this.qtdItem = qtdItem;
        this.total = total;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public int getQtdItem() {
        return qtdItem;
    }

    public void setQtdItem(int qtdItem) {
        this.qtdItem = qtdItem;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
