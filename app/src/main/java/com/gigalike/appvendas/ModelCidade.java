package com.gigalike.appvendas;

public class ModelCidade {

    private String nomeCidade;
    private String unidadeFederativa;
    private String codigoIBGE;

    //construtor com parametros
    public ModelCidade(String nomeCidade, String unidadeFederativa, String codigoIBGE) {
        this.nomeCidade = nomeCidade;
        this.unidadeFederativa = unidadeFederativa;
        this.codigoIBGE = codigoIBGE;
    }

    //Construtor sem parametros
    public ModelCidade(){}

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getUnidadeFederativa() {
        return unidadeFederativa;
    }

    public void setUnidadeFederativa(String unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
    }


}
