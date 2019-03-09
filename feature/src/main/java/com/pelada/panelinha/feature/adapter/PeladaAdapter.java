package com.pelada.panelinha.feature.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pelada.panelinha.feature.JogadoresActivity;
import com.pelada.panelinha.feature.R;
import com.pelada.panelinha.feature.modelo.Pelada;

import java.util.ArrayList;

public class PeladaAdapter extends RecyclerView.Adapter<PeladaAdapter.MyViewHolder> {
    private ArrayList<Pelada> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_nome;
        public TextView tv_local;
        public TextView tv_hora;
        public TextView tv_quant;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            textView = (TextView) itemView;
            tv_nome = itemView.findViewById(R.id.item_nome);
            tv_local = itemView.findViewById(R.id.item_local);
            tv_hora = itemView.findViewById(R.id.item_hora);
            tv_quant = itemView.findViewById(R.id.item_quant);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("peladaactivity", "chegou no click");
            Pelada item = mDataset.get(getLayoutPosition());
            Intent intent = new Intent(context, JogadoresActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("pelada", item);
            Log.i("PeladaAdapter", "peladaParse"+item);
            intent.putExtra("pelada", item);
            context.startActivity(intent);
        }
    }

    public PeladaAdapter(ArrayList myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    public PeladaAdapter() {
    }

    @NonNull
    @Override
    public PeladaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_pelada, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PeladaAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_nome.setText(mDataset.get(i).getNome());
        myViewHolder.tv_local.setText(mDataset.get(i).getLocal());
        myViewHolder.tv_hora.setText(mDataset.get(i).getHora());
        myViewHolder.tv_quant.setText(mDataset.get(i).getQuantidade());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

//    public Pelada getItem(int position) {
//        return mDataset.get(position);
//    }
//
//    public void removeItem(int position) {
//        mDataset.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void restoreItem(Pelada item, int position) {
//        mDataset.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public ArrayList<Pelada> getData() {
//        return mDataset;
//    }
}
