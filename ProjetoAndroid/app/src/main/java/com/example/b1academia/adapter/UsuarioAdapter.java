package com.example.b1academia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.b1academia.R;
import com.example.b1academia.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> lista;

    public UsuarioAdapter(Context context, List<Usuario> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
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

        txtNomeUsuarioItem.setText(usuario.getNome());

        txtDetalhesUsuarioItem.setText(
                "Peso: " + usuario.getPeso()
                        + " | Altura: " + usuario.getAltura()
                        + " | Meta: " + usuario.getMeta()
        );

        return item;
    }
}