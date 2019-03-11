package com.pelada.panelinha.feature.services;

import com.pelada.panelinha.feature.modelo.Estatistica;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-pelada-85ed8.cloudfunctions.net/panelinha/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public PeladaService getPeladaService() {
        return this.retrofit.create(PeladaService.class);
    }

    public JogadorService getJogadorService() {
        return this.retrofit.create(JogadorService.class);
    }

    public EstatisticaService getEstatisticaService() {
        return this.retrofit.create(EstatisticaService.class);
    }
}
