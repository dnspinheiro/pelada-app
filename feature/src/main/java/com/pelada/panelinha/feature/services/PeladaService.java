package com.pelada.panelinha.feature.services;

import com.pelada.panelinha.feature.modelo.Retorno;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PeladaService {
    @GET("pelada/get")
    Call<Retorno> buscarPeladas();

    @GET("pelada/getById/{id}")
    Call<Retorno> buscarPelada(@Path("id") String id);
}
