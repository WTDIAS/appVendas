package com.gigalike.appvendas;

public class ModelProduto {
    private String codigo;
    private String descricao;
    private String cod_barras;
    private String estoque;
    private String pvenda;
    private String departamento;
    private String pliquido;
    private String pbruto;
    private String ncm;
    private String tamanho;
    private String pcusto;
    private String unidade;
    private String gondola;
    private String familia;
    private String localizacao;
    private String cst;
    private String aliq_icms;
    private String cest;
    private String piscof;
    private String observacao;
    private String atualizado;
    private String situacao;
    private int selecionado;



    //CONTRUTOR
    public ModelProduto(String codigo, String descricao, String cod_barras, String estoque, String pvenda, String departamento, String pliquido, String pbruto, String ncm, String tamanho, String pcusto, String unidade, String gondola, String familia, String localizacao, String cst, String aliq_icms, String cest, String piscof, String observacao, String atualizado, String situacao, int selecionado) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.cod_barras = cod_barras;
        this.estoque = estoque;
        this.pvenda = pvenda;
        this.departamento = departamento;
        this.pliquido = pliquido;
        this.pbruto = pbruto;
        this.ncm = ncm;
        this.tamanho = tamanho;
        this.pcusto = pcusto;
        this.unidade = unidade;
        this.gondola = gondola;
        this.familia = familia;
        this.localizacao = localizacao;
        this.cst = cst;
        this.aliq_icms = aliq_icms;
        this.cest = cest;
        this.piscof = piscof;
        this.observacao = observacao;
        this.atualizado = atualizado;
        this.situacao = situacao;
        this.selecionado = selecionado;
    }

    public boolean getSelecionado(){
        if(selecionado == 1)
            return true;
        else
            return false;
    }

    public void setSelecionado(boolean selecionado){
        if (selecionado)
            this.selecionado = 1;
        else
            this.selecionado = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public String getPvenda() {
        return pvenda;
    }

    public void setPvenda(String pvenda) {
        this.pvenda = pvenda;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPliquido() {
        return pliquido;
    }

    public void setPliquido(String pliquido) {
        this.pliquido = pliquido;
    }

    public String getPbruto() {
        return pbruto;
    }

    public void setPbruto(String pbruto) {
        this.pbruto = pbruto;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getPcusto() {
        return pcusto;
    }

    public void setPcusto(String pcusto) {
        this.pcusto = pcusto;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getGondola() {
        return gondola;
    }

    public void setGondola(String gondola) {
        this.gondola = gondola;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getAliq_icms() {
        return aliq_icms;
    }

    public void setAliq_icms(String aliq_icms) {
        this.aliq_icms = aliq_icms;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
    }

    public String getPiscof() {
        return piscof;
    }

    public void setPiscof(String piscof) {
        this.piscof = piscof;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(String atualizado) {
        this.atualizado = atualizado;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
