package com.pelada.panelinha.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pelada.panelinha.feature.modelo.Jogador;
import com.pelada.panelinha.feature.modelo.RetornoJogador;
import com.pelada.panelinha.feature.services.RetrofitConfig;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovoJogador extends AppCompatActivity {
    Jogador jogador = new Jogador();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogador);

        final EditText edt_nome = findViewById(R.id.edt_nome);
        final EditText edt_nivel = findViewById(R.id.edt_nivel);
        final EditText edt_posicao = findViewById(R.id.edt_posicao);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nome = edt_nome.getText().toString();
                String nivel = edt_nivel.getText().toString();
                String posicao = edt_posicao.getText().toString();

                jogador.setNivel(nivel);
                jogador.setPosicao(posicao);
                jogador.setNome(nome);

                final String json  = gson.toJson(jogador);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                Call<RetornoJogador> call = new RetrofitConfig().getJogadorService().postJogador(body);
                call.enqueue(new Callback<RetornoJogador>() {
                    @Override
                    public void onResponse(Call<RetornoJogador> call, Response<RetornoJogador> response) {
                        RetornoJogador retorno = response.body();
                        Log.i("NovoJogadorActivity", retorno.getResult().toString());
                        Log.i("NovoJogadorActivity", retorno.getMessage());
                        startActivity(new Intent(NovoJogador.this, JogadoresActivity.class));
                    }

                    @Override
                    public void onFailure(Call<RetornoJogador> call, Throwable t) {
                        Log.e("JogadorService   ", "Erro ao enviar Jogador:" + t.getMessage());
                    }
                });

            }
        });
    }
}
