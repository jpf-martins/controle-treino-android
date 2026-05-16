package com.example.b1academia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.b1academia.R;
import com.example.b1academia.model.ExecucaoApp;

import java.util.List;
import java.util.Map;

public class ExecucaoAdapter extends ArrayAdapter<ExecucaoApp> {
    private Context context;
    private List<ExecucaoApp> lista;
    private Map<Integer, String> mapaExercicios;
    private OnExcluirClickListener onExcluirClickListener;

    public interface OnExcluirClickListener {
        void onExcluirClick(ExecucaoApp execucao);
    }

    public ExecucaoAdapter(Context context, List<ExecucaoApp> lista, Map<Integer, String> mapaExercicios, OnExcluirClickListener onExcluirClickListener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.mapaExercicios = mapaExercicios;
        this.onExcluirClickListener = onExcluirClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_execucao, parent, false);
        }

        final ExecucaoApp execucao = lista.get(position);

        TextView txtDataExecucaoItem = item.findViewById(R.id.txtDataExecucaoItem);
        TextView txtDetalhesExecucaoItem = item.findViewById(R.id.txtDetalhesExecucaoItem);
        Button btnExcluirExecucaoItem = item.findViewById(R.id.btnExcluirExecucaoItem);

        txtDataExecucaoItem.setText(execucao.getData());

        String nomeExercicio = mapaExercicios.get(execucao.getExercicioId());
        if (nomeExercicio == null) {
            nomeExercicio = "Exercício não encontrado";
        }

        txtDetalhesExecucaoItem.setText(
                "Exercício: " + nomeExercicio +
                        " | Carga: " + execucao.getCarga() +
                        " | Obs: " + execucao.getObservacao()
        );

        btnExcluirExecucaoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExcluirClickListener != null) {
                    onExcluirClickListener.onExcluirClick(execucao);
                }
            }
        });

        return item;
    }
}