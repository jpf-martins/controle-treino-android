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

    public interface OnExcluirClickListener {
        void onExcluirClick(GrupoMuscular grupo);
    }

    private Context context;
    private List<GrupoMuscular> lista;
    private OnExcluirClickListener listener;

    public GrupoMuscularAdapter(Context context, List<GrupoMuscular> lista, OnExcluirClickListener listener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_grupo, parent, false);
        }

        GrupoMuscular grupo = lista.get(position);

        TextView txtItemGrupo = item.findViewById(R.id.txtItemGrupo);
        Button btnExcluirGrupoItem = item.findViewById(R.id.btnExcluirGrupoItem);

        txtItemGrupo.setText(grupo.getNome());

        btnExcluirGrupoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onExcluirClick(grupo);
            }
        });

        return item;
    }
}