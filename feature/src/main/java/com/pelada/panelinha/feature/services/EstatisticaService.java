package com.pelada.panelinha.feature.services;

import com.pelada.panelinha.feature.modelo.RetornoEstatistica;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstatisticaService {
    @GET("estatistica/get")
    Call<RetornoEstatistica> buscarEstatistica();

    @POST("estatistica/post")
    Call<RetornoEstatistica> postEstatistica(@Body RequestBody object);

    @DELETE("estatistica/delete/{id}")
    Call<RetornoEstatistica> deleteEstatistica(@Path("id") String id);

    @PUT("estatistica/update/{id}")
    Call<RetornoEstatistica> updateEstatistica(@Path("id") String id);

    @GET("estatistica/getById/{id}")
    Call<RetornoEstatistica> buscarEstatistica(@Path("id") String id);
}
