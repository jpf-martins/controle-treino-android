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

    public interface OnExcluirClickListener {
        void onExcluirClick(ExecucaoApp execucao);
    }

    private Context context;
    private List<ExecucaoApp> lista;
    private Map<Integer, String> mapaExercicios;
    private Map<Integer, String> mapaTreinos;
    private OnExcluirClickListener listener;

    public ExecucaoAdapter(Context context, List<ExecucaoApp> lista, Map<Integer, String> mapaExercicios, Map<Integer, String> mapaTreinos, OnExcluirClickListener listener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.mapaExercicios = mapaExercicios;
        this.mapaTreinos = mapaTreinos;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_execucao, parent, false);
        }

        ExecucaoApp execucao = lista.get(position);

        TextView txtDataExecucaoItem = item.findViewById(R.id.txtDataExecucaoItem);
        TextView txtDetalhesExecucaoItem = item.findViewById(R.id.txtDetalhesExecucaoItem);
        Button btnExcluirExecucaoItem = item.findViewById(R.id.btnExcluirExecucaoItem);

        String nomeExercicio = mapaExercicios.get(execucao.getExercicioId());
        String nomeTreino = mapaTreinos.get(execucao.getTreinoId());

        if (nomeExercicio == null) {
            nomeExercicio = "Exercício ID: " + execucao.getExercicioId();
        }

        if (nomeTreino == null) {
            nomeTreino = "Treino ID: " + execucao.getTreinoId();
        }

        txtDataExecucaoItem.setText(execucao.getData());

        txtDetalhesExecucaoItem.setText(
                "Treino: " + nomeTreino
                        + " | Exercício: " + nomeExercicio
                        + " | Carga: " + execucao.getCarga()
                        + " | Obs: " + execucao.getObservacao()
        );

        btnExcluirExecucaoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onExcluirClick(execucao);
            }
        });

        return item;
    }
}