package com.example.b1academia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.b1academia.R;
import com.example.b1academia.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    public interface OnExcluirClickListener {
        void onExcluirClick(Usuario usuario);
    }

    private Context context;
    private List<Usuario> lista;
    private OnExcluirClickListener listener;

    public UsuarioAdapter(Context context, List<Usuario> lista, OnExcluirClickListener listener) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        }

        Usuario usuario = lista.get(position);

        TextView txtNomeUsuarioItem = item.findViewById(R.id.txtNomeUsuarioItem);
        TextView txtDetalhesUsuarioItem = item.findViewById(R.id.txtDetalhesUsuarioItem);
        Button btnExcluirUsuarioItem = item.findViewById(R.id.btnExcluirUsuarioItem);

        txtNomeUsuarioItem.setText(usuario.getNome());

        txtDetalhesUsuarioItem.setText(
                "Peso: " + usuario.getPeso()
                        + " | Altura: " + usuario.getAltura()
                        + " | Meta: " + usuario.getMeta()
        );

        btnExcluirUsuarioItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onExcluirClick(usuario);
            }
        });

        return item;
    }
}