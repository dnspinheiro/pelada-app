package com.pelada.panelinha.feature.modelo;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Estatistica implements Serializable {
    private Pelada pelada;
    private String pereba;
    private String craque;
    private String paredao;
    private String artilheiro;
    private Integer quantTimes;
    private Integer quantJogTimes;
    private ArrayList<Time> times;

    public Pelada getPelada() {
        return pelada;
    }

    public void setPelada(Pelada pelada) {
        this.pelada = pelada;
    }

    public String getPereba() {
        return pereba;
    }

    public void setPereba(String pereba) {
        this.pereba = pereba;
    }

    public String getCraque() {
        return craque;
    }

    public void setCraque(String craque) {
        this.craque = craque;
    }

    public String getParedao() {
        return paredao;
    }

    public void setParedao(String paredao) {
        this.paredao = paredao;
    }

    public String getArtilheiro() {
        return artilheiro;
    }

    public void setArtilheiro(String artilheiro) {
        this.artilheiro = artilheiro;
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Time> times) {
        this.times = times;
    }

    public Integer getQuantTimes() {
        return quantTimes;
    }

    public void setQuantTimes(Integer quantTimes) {
        this.quantTimes = quantTimes;
    }

    public Integer getQuantJogTimes() {
        return 6;
    }

    public void setQuantJogTimes(Integer quantJogTimes) {
        this.quantJogTimes = quantJogTimes;
    }
}
