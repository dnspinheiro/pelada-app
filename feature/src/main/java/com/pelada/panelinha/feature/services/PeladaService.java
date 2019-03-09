package com.pelada.panelinha.feature.services;

import com.pelada.panelinha.feature.modelo.RetornoPelada;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PeladaService {
    @GET("pelada/get")
    Call<RetornoPelada> buscarPeladas();

    @POST("pelada/post")
    Call<RetornoPelada> postPelada(@Body RequestBody object);

    @DELETE("pelada/delete/{id}")
    Call<RetornoPelada> deletePelada(@Path("id") String id);

    @PUT("pelada/update/{id}")
    Call<RetornoPelada> updatePelada(@Path("id") String id);

    @GET("pelada/getById/{id}")
    Call<RetornoPelada> buscarPelada(@Path("id") String id);
}
