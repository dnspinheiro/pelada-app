package com.pelada.panelinha.feature.modelo;

import java.io.Serializable;

public class Jogador implements Serializable {

    private String id;
    private String nome;
    private String nivel;
    private String posicao;
    private boolean participa;

    public Jogador(String nome, String nivel, String posicao){
        this.nome = nome;
        this.nivel = nivel;
        this.posicao = posicao;
    }

    public Jogador(Jogador jogador){
        this.participa = jogador.getParticipa();
        this.posicao = jogador.getPosicao();
        this.nivel = jogador.getNivel();
        this.nome = jogador.getNome();
        this.id = jogador.getId();
    }

    public Jogador(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getParticipa() {
        return participa;
    }

    public void setParticipa(boolean participa) {
        this.participa = participa;
    }

    @Override
    public String toString() {
        return " " + nome;
    }
}
