package com.pelada.panelinha.feature;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pelada.panelinha.feature.modelo.Estatistica;
import com.pelada.panelinha.feature.modelo.RetornoEstatistica;
import com.pelada.panelinha.feature.modelo.RetornoJogador;
import com.pelada.panelinha.feature.services.RetrofitConfig;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstatisticaActivity extends AppCompatActivity {
    Gson gson = new Gson();

    private Estatistica estatistica = new Estatistica();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        estatistica = (Estatistica) getIntent().getSerializableExtra("estatistica3");
        Log.i("EstatActivity", "timesPut " + estatistica.getQuantTimes());

        final EditText edt_craque = findViewById(R.id.edt_craque);
        final EditText edt_pereba = findViewById(R.id.edt_pereba);
        final EditText edt_artilheiro = findViewById(R.id.edt_artilheiro);
        final EditText edt_paredao = findViewById(R.id.edt_paredao);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                estatistica.setCraque(edt_craque.getText().toString());
                estatistica.setPereba(edt_pereba.getText().toString());
                estatistica.setArtilheiro(edt_artilheiro.getText().toString());
                estatistica.setParedao(edt_paredao.getText().toString());

                final String json = gson.toJson(estatistica);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                Call<RetornoEstatistica> call = new RetrofitConfig().getEstatisticaService().postEstatistica(body);
                call.enqueue(new Callback<RetornoEstatistica>() {
                    @Override
                    public void onResponse(Call<RetornoEstatistica> call, Response<RetornoEstatistica> response) {
                        RetornoEstatistica retorno = response.body();
                        Log.i("NovoJogadorActivity", retorno.getResult().toString());
                        Toast.makeText(getApplicationContext(),retorno.getMessage(), Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(NovoJogador.this, JogadoresActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<RetornoEstatistica> call, Throwable t) {
                        Log.e("JogadorService   ", "Erro ao enviar Jogador:" + t.getMessage());
                    }
                });
            }
        });
    }

}
