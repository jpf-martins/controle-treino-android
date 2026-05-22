package com.example.b1academia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.b1academia.R;
import com.example.b1academia.model.Treino;

import java.util.List;

public class TreinoAdapter extends ArrayAdapter<Treino> {

    public interface OnExcluirClickListener {
        void onExcluirClick(Treino treino);
    }

    private Context context;
    private List<Treino> lista;
    private OnExcluirClickListener listener;

    public TreinoAdapter(Context context, List<Treino> lista, OnExcluirClickListener listener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_treino, parent, false);
        }

        Treino treino = lista.get(position);

        TextView txtNomeTreinoItem = item.findViewById(R.id.txtNomeTreinoItem);
        TextView txtDescricaoTreinoItem = item.findViewById(R.id.txtDescricaoTreinoItem);
        Button btnExcluirTreinoItem = item.findViewById(R.id.btnExcluirTreinoItem);

        txtNomeTreinoItem.setText(treino.getNome());
        txtDescricaoTreinoItem.setText(treino.getDescricao());

        btnExcluirTreinoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onExcluirClick(treino);
            }
        });

        return item;
    }
}