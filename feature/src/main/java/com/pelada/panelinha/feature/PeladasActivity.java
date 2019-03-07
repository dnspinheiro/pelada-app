package com.pelada.panelinha.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pelada.panelinha.feature.adapter.PeladaAdapter;
import com.pelada.panelinha.feature.modelo.Pelada;
import com.pelada.panelinha.feature.modelo.RetornoPelada;
import com.pelada.panelinha.feature.services.RetrofitConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeladasActivity extends AppCompatActivity {

    private ArrayList<Pelada> peladas;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peladas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPeladas();
        recyclerView = (RecyclerView) findViewById(R.id.rv_peladas);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_peladas);
//        rv.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(PeladasActivity.this, NovaPelada.class));
            }
        });
    }

    private void getPeladas() {
        Log.i("PeladaActivity", "chegou no get Peladas");
        Call<RetornoPelada> call = new RetrofitConfig().getPeladaService().buscarPeladas();
        call.enqueue(new Callback<RetornoPelada>() {
            @Override
            public void onResponse(Call<RetornoPelada> call, Response<RetornoPelada> response) {
                RetornoPelada retornoPelada = response.body();
                peladas = retornoPelada.getResult();
                // specify an adapter (see also next example)
                mAdapter = new PeladaAdapter(peladas);
                recyclerView.setAdapter(mAdapter);
//                resposta.setText(peladas.toString());
                Log.i("PeladaActivity", retornoPelada.getResult().toString());
                Log.i("PeladaActivity", retornoPelada.getMessage());
            }

            @Override
            public void onFailure(Call<RetornoPelada> call, Throwable t) {
                Log.e("PeladaService   ", "Erro ao buscar as Peladas:" + t.getMessage());
            }
        });
    }
}
