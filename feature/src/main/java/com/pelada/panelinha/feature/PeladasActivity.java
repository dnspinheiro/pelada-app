package com.pelada.panelinha.feature;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
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
    private PeladaAdapter mAdapter;
    //    private RecyclerView.LayoutManager layoutManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peladas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        getPeladas();
        recyclerView = (RecyclerView) findViewById(R.id.rv_peladas);
        recyclerView.setHasFixedSize(true);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeladasActivity.this, NovaPelada.class));
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void getPeladas() {
        Call<RetornoPelada> call = new RetrofitConfig().getPeladaService().buscarPeladas();
        call.enqueue(new Callback<RetornoPelada>() {
            @Override
            public void onResponse(Call<RetornoPelada> call, Response<RetornoPelada> response) {
                RetornoPelada retornoPelada = response.body();
                peladas = retornoPelada.getResult();
                // specify an adapter (see also next example)
                mAdapter = new PeladaAdapter(peladas, PeladasActivity.this);
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
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
//                final Pelada item = mAdapter.getData().get(position);
                final Pelada item = peladas.get(position);


//                mAdapter.removeItem(position);
                peladas.remove(position);
                mAdapter.notifyItemRemoved(position);

                //delete pelada firebase
                Call<RetornoPelada> call = new RetrofitConfig().getPeladaService().deletePelada(item.getId());
                call.enqueue(new Callback<RetornoPelada>() {
                    @Override
                    public void onResponse(Call<RetornoPelada> call, Response<RetornoPelada> response) {
                        RetornoPelada retornoPelada = response.body();

                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                peladas.add(position, item);
                                mAdapter.notifyItemInserted(position);
                                recyclerView.scrollToPosition(position);
                            }
                        });

                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();
                        Log.i("PeladaActivity", retornoPelada.getResult().toString());
                        Log.i("PeladaActivity", retornoPelada.getMessage());
                    }

                    @Override
                    public void onFailure(Call<RetornoPelada> call, Throwable t) {
                        Log.e("PeladaService   ", "Erro ao buscar as Peladas:" + t.getMessage());
                    }
                });
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_peladas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }

        if (id == R.id.action_refresh) {
            getPeladas();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
