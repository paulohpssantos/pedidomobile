package com.example.pedidomobile.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

public class Cabecalho extends SugarRecord {

    @Unique
    private Long numPedido;
    private String data;
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;

    private List<Produto> produtos;
    private Resumo resumo;

    public Cabecalho() {
    }

    public Cabecalho(Long numPedido, String data, String razaoSocial, String cnpj, String telefone,
                     String email, List<Produto> produtos, Resumo resumo) {
        this.numPedido = numPedido;
        this.data = data;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.produtos = produtos;
        this.resumo = resumo;
    }

    public Long getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Long numPedido) {
        this.numPedido = numPedido;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Resumo getResumo() {
        return resumo;
    }

    public void setResumo(Resumo resumo) {
        this.resumo = resumo;
    }
}
