package com.pelada.panelinha.feature.services;

import com.pelada.panelinha.feature.modelo.RetornoJogador;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JogadorService {
    @GET("jogador/get")
    Call<RetornoJogador> buscarJogadores();

    @POST("jogador/post")
    Call<RetornoJogador> postJogador(@Body RequestBody object);

    @DELETE("jogador/delete/{id}")
    Call<RetornoJogador> deleteJogador(@Path("id") String id);

    @PUT("jogador/update/{id}")
    Call<RetornoJogador> updateJogador(@Path("id") String id);

    @GET("jogador/getById/{id}")
    Call<RetornoJogador> buscarJogador(@Path("id") String id);
}
