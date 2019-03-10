package com.pelada.panelinha.feature.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Time implements Serializable {
    private String nome;
    private ArrayList<Jogador> jogadores;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
