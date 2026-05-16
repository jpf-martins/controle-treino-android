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

public class ExercicioAdapter extends ArrayAdapter<Exercicio> {
    private Context context;
    private List<Exercicio> lista;
    private OnExcluirClickListener onExcluirClickListener;

    public interface OnExcluirClickListener {
        void onExcluirClick(Exercicio exercicio);
    }

    public ExercicioAdapter(Context context, List<Exercicio> lista, OnExcluirClickListener onExcluirClickListener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.onExcluirClickListener = onExcluirClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_exercicio, parent, false);
        }

        final Exercicio exercicio = lista.get(position);

        TextView txtNomeExercicioItem = item.findViewById(R.id.txtNomeExercicioItem);
        TextView txtDetalhesExercicioItem = item.findViewById(R.id.txtDetalhesExercicioItem);
        Button btnExcluirExercicioItem = item.findViewById(R.id.btnExcluirExercicioItem);

        txtNomeExercicioItem.setText(exercicio.getNome());
        txtDetalhesExercicioItem.setText(
                "Séries: " + exercicio.getSeries() + " | Repetições: " + exercicio.getRepeticoes()
        );

        btnExcluirExercicioItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExcluirClickListener != null) {
                    onExcluirClickListener.onExcluirClick(exercicio);
                }
            }
        });

        return item;
    }
}