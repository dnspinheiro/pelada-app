package com.pelada.panelinha.feature;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

//        pelada.setHora("Sab, 18:00");
//        pelada.setLocal("Campinhos da DuRei");
//        pelada.setNome("pelada do sab");
//        pelada.setQuantidade("22");

        final EditText edt_nome = findViewById(R.id.edt_nome);
        final EditText edt_local = findViewById(R.id.edt_local);
        final EditText edt_hora = findViewById(R.id.edt_hora);
        final EditText edt_quant = findViewById(R.id.edt_quant);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("NovaPeladaActivity", "chegou no post Pelada");
                String nome = edt_nome.getText().toString();
                String local = edt_local.getText().toString();
                String hora = edt_hora.getText().toString();
                String quant = edt_quant.getText().toString();

                pelada.setHora(hora);
                pelada.setLocal(local);
                pelada.setNome(nome);
                pelada.setQuantidade(quant);

                final String json  = gson.toJson(pelada);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                Call<RetornoPelada> call = new RetrofitConfig().getPeladaService().postPelada(body);
                call.enqueue(new Callback<RetornoPelada>() {
                    @Override
                    public void onResponse(Call<RetornoPelada> call, Response<RetornoPelada> response) {
                        RetornoPelada retornoPelada = response.body();
                        Log.i("NovaPeladaActivity", retornoPelada.getResult().toString());
                        Log.i("NovaPeladaActivity", retornoPelada.getMessage());
                        startActivity(new Intent(NovaPelada.this, PeladasActivity.class));
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
