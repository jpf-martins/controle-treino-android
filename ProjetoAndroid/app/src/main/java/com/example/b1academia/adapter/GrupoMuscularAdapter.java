package com.example.b1academia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.b1academia.R;
import com.example.b1academia.model.GrupoMuscular;

import java.util.List;

public class GrupoMuscularAdapter extends ArrayAdapter<GrupoMuscular> {
    private Context context;
    private List<GrupoMuscular> lista;
    private OnExcluirClickListener onExcluirClickListener;

    public interface OnExcluirClickListener {
        void onExcluirClick(GrupoMuscular grupo);
    }

    public GrupoMuscularAdapter(Context context, List<GrupoMuscular> lista, OnExcluirClickListener onExcluirClickListener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.onExcluirClickListener = onExcluirClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_grupo, parent, false);
        }

        final GrupoMuscular grupo = lista.get(position);

        TextView txtItemGrupo = item.findViewById(R.id.txtItemGrupo);
        Button btnExcluirGrupoItem = item.findViewById(R.id.btnExcluirGrupoItem);

        txtItemGrupo.setText(grupo.getNome());

        btnExcluirGrupoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExcluirClickListener != null) {
                    onExcluirClickListener.onExcluirClick(grupo);
                }
            }
        });

        return item;
    }
}