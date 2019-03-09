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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pelada.panelinha.feature.adapter.JogadorAdapter;
import com.pelada.panelinha.feature.modelo.Jogador;
import com.pelada.panelinha.feature.modelo.Pelada;
import com.pelada.panelinha.feature.modelo.RetornoJogador;
import com.pelada.panelinha.feature.services.RetrofitConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JogadoresActivity extends AppCompatActivity {

    private ArrayList<Jogador> jogadores;
    private Pelada pelada;

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

        pelada = (Pelada) getIntent().getSerializableExtra("pelada");
        Log.i("JogadoresActivity", "peladaParse" + pelada.getNome());

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
                for (Jogador j : jogadores) {
                    j.setParticipa(false);
                }

                // specify an adapter (see also next example)
                mAdapter = new JogadorAdapter(jogadores, JogadoresActivity.this);
                recyclerView.setAdapter(mAdapter);
                enableSwipeToDeleteAndUndo();
            }

            @Override
            public void onFailure(Call<RetornoJogador> call, Throwable t) {
                Log.e("JogadorActivity   ", "Erro ao buscar as Jogadores:" + t.getMessage());
            }
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final Jogador item = jogadores.get(position);

                jogadores.remove(position);
                mAdapter.notifyItemRemoved(position);

                //delete jogadr firebase
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
                        Log.i("JogadorActivity", retorno.getResult().toString());
                        Log.i("JogadorActivity", retorno.getMessage());
                    }

                    @Override
                    public void onFailure(Call<RetornoJogador> call, Throwable t) {
                        Log.e("JogadorService   ", "Erro ao buscar as Peladas:" + t.getMessage());
                    }
                });
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jogadores, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_sortear) {

            int quant = jogadores.size();
            int quantJogTime = 6;
            int quantTimes;
            if (quant % quantJogTime == 0) {
                quantTimes = quant / quantJogTime;
            } else {
                quantTimes = quant / quantJogTime + 1;
            }

            Intent intent = new Intent(JogadoresActivity.this, TimesActivity.class);
            Log.i("PeladaAdapter", "peladaParse"+quantTimes);
            intent.putExtra("times", quantTimes);
            startActivity(intent);

            return true;
        } else {// If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);
        }
    }
}
