package com.pelada.panelinha.feature;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.pelada.panelinha.feature.adapter.PeladaAdapter;
import com.pelada.panelinha.feature.modelo.Pelada;
import com.pelada.panelinha.feature.modelo.RetornoPelada;
import com.pelada.panelinha.feature.services.RetrofitConfig;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovaPelada extends AppCompatActivity {

    Pelada pelada = new Pelada();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_pelada);

        pelada.setHora("Sab, 18:00");
        pelada.setLocal("Campinhos da DuRei");
        pelada.setNome("pelada do sab");
        pelada.setQuantidade("22");

        

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("NovaPeladaActivity", "chegou no post Pelada");
                final String json  = gson.toJson(pelada);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                Call<RetornoPelada> call = new RetrofitConfig().getPeladaService().postPelada(body);
                call.enqueue(new Callback<RetornoPelada>() {
                    @Override
                    public void onResponse(Call<RetornoPelada> call, Response<RetornoPelada> response) {
                        RetornoPelada retornoPelada = response.body();
                        Log.i("NovaPeladaActivity", retornoPelada.getResult().toString());
                        Log.i("NovaPeladaActivity", retornoPelada.getMessage());
                    }

                    @Override
                    public void onFailure(Call<RetornoPelada> call, Throwable t) {
                        Log.e("PeladaService   ", "Erro ao enviar Pelada:" + t.getMessage());
                    }
                });

            }
        });
    }
}
