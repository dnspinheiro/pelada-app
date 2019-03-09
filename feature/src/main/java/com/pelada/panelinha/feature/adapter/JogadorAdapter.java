package com.pelada.panelinha.feature.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pelada.panelinha.feature.R;
import com.pelada.panelinha.feature.modelo.Jogador;

import java.util.ArrayList;

public class JogadorAdapter extends RecyclerView.Adapter<JogadorAdapter.MyViewHolder> {
    private ArrayList<Jogador> mDataset;
    private Context context;

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tv_nome;
        public TextView tv_nivel;
        public TextView tv_posicao;
        public CheckBox chk_participa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nome = itemView.findViewById(R.id.item_nome);
            tv_nivel = itemView.findViewById(R.id.item_nivel);
            tv_posicao = itemView.findViewById(R.id.item_posicao);
            chk_participa = itemView.findViewById(R.id.chk_participa);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public JogadorAdapter(ArrayList myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @NonNull
    @Override
    public JogadorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_jogador, viewGroup, false);

        JogadorAdapter.MyViewHolder vh = new JogadorAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull JogadorAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_nome.setText(mDataset.get(i).getNome());
        myViewHolder.tv_nivel.setText(mDataset.get(i).getNivel());
        myViewHolder.tv_posicao.setText(mDataset.get(i).getPosicao());
        myViewHolder.chk_participa.setChecked(mDataset.get(i).getParticipa());

        myViewHolder.chk_participa.setOnCheckedChangeListener(null);
        myViewHolder.chk_participa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                mDataset.get(i).setParticipa(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
