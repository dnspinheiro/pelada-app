package com.pelada.panelinha.feature;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private RecyclerView.Adapter mAdapter = new PeladaAdapter();
    //    private RecyclerView.LayoutManager layoutManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peladas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPeladas();
        recyclerView = (RecyclerView) findViewById(R.id.rv_peladas);
        recyclerView.setHasFixedSize(true);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

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
                enableSwipeToDeleteAndUndo();
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

    private void enableSwipeToDeleteAndUndo() {
        Log.i("PeladasActivity", "chegou no swipe");
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Log.i("PeladasActivity", "swiped 2");

                final int position = viewHolder.getAdapterPosition();
//                final Pelada item = mAdapter.getData().get(position);
                final Pelada item = peladas.get(position);


//                mAdapter.removeItem(position);
                peladas.remove(position);
                mAdapter.notifyItemRemoved(position);

                //delete pelada firebase


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        mAdapter.restoreItem(item, position);
                        peladas.add(position, item);
                        mAdapter.notifyItemInserted(position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
