package com.example.b1academia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.b1academia.R;
import com.example.b1academia.model.Exercicio;

import java.util.List;
import java.util.Map;

public class ExercicioAdapter extends ArrayAdapter<Exercicio> {

    public interface OnExcluirClickListener {
        void onExcluirClick(Exercicio exercicio);
    }

    private Context context;
    private List<Exercicio> lista;
    private Map<Integer, String> mapaGrupos;
    private OnExcluirClickListener listener;

    public ExercicioAdapter(Context context, List<Exercicio> lista, Map<Integer, String> mapaGrupos, OnExcluirClickListener listener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.mapaGrupos = mapaGrupos;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_exercicio, parent, false);
        }

        Exercicio exercicio = lista.get(position);

        TextView txtNomeExercicioItem = item.findViewById(R.id.txtNomeExercicioItem);
        TextView txtDetalhesExercicioItem = item.findViewById(R.id.txtDetalhesExercicioItem);
        Button btnExcluirExercicioItem = item.findViewById(R.id.btnExcluirExercicioItem);

        String nomeGrupo = mapaGrupos.get(exercicio.getGrupoId());

        if (nomeGrupo == null) {
            nomeGrupo = "Grupo ID: " + exercicio.getGrupoId();
        }

        txtNomeExercicioItem.setText(exercicio.getNome());

        txtDetalhesExercicioItem.setText(
                "Grupo: " + nomeGrupo
                        + " | Séries: " + exercicio.getSeries()
                        + " | Repetições: " + exercicio.getRepeticoes()
        );

        btnExcluirExercicioItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onExcluirClick(exercicio);
            }
        });

        return item;
    }
}