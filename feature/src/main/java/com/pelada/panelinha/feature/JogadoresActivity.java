package com.pelada.panelinha.feature;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.pelada.panelinha.feature.adapter.JogadorAdapter;
import com.pelada.panelinha.feature.modelo.Jogador;
import com.pelada.panelinha.feature.modelo.RetornoJogador;
import com.pelada.panelinha.feature.services.RetrofitConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JogadoresActivity extends AppCompatActivity {

    private ArrayList<Jogador> jogadores;

    private RecyclerView recyclerView;
    private JogadorAdapter mAdapter;
    //    private RecyclerView.LayoutManager layoutManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getJogadores();
        recyclerView = (RecyclerView) findViewById(R.id.rv_jogadores);
        recyclerView.setHasFixedSize(true);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JogadoresActivity.this, NovoJogador.class));
            }
        });
    }

    private void getJogadores() {
        Call<RetornoJogador> call = new RetrofitConfig().getJogadorService().buscarJogadores();
        call.enqueue(new Callback<RetornoJogador>() {
            @Override
            public void onResponse(Call<RetornoJogador> call, Response<RetornoJogador> response) {
                RetornoJogador retorno = response.body();
                jogadores = retorno.getResult();
                // specify an adapter (see also next example)
                mAdapter = new JogadorAdapter(jogadores, JogadoresActivity.this);
                recyclerView.setAdapter(mAdapter);
                enableSwipeToDeleteAndUndo();
//                resposta.setText(peladas.toString());
//                Log.i("JogadorActivity", RetornoJogador.getStatus());
//                Log.i("JogadorActivity", RetornoJogador.getMessage());
            }

            @Override
            public void onFailure(Call<RetornoJogador> call, Throwable t) {
                Log.e("JogadorActivity   ", "Erro ao buscar as Peladas:" + t.getMessage());
            }
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
//                final Pelada item = mAdapter.getData().get(position);
                final Jogador item = jogadores.get(position);


//                mAdapter.removeItem(position);
                jogadores.remove(position);
                mAdapter.notifyItemRemoved(position);

                //delete pelada firebase
                Call<RetornoJogador> call = new RetrofitConfig().getJogadorService().deleteJogador(item.getId());
                call.enqueue(new Callback<RetornoJogador>() {
                    @Override
                    public void onResponse(Call<RetornoJogador> call, Response<RetornoJogador> response) {
                        RetornoJogador retorno = response.body();

                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                jogadores.add(position, item);
                                mAdapter.notifyItemInserted(position);
                                recyclerView.scrollToPosition(position);
                            }
                        });

                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();
                        Log.i("PeladaActivity", retorno.getResult().toString());
                        Log.i("PeladaActivity", retorno.getMessage());
                    }

                    @Override
                    public void onFailure(Call<RetornoJogador> call, Throwable t) {
                        Log.e("PeladaService   ", "Erro ao buscar as Peladas:" + t.getMessage());
                    }
                });
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
