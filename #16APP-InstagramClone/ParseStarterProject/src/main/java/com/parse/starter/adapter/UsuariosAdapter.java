package com.parse.starter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.starter.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DouglasNickson on 09/10/2017.
 */

public class UsuariosAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(Context c, ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){

            //Inicializar objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a view a partir do xml
            view = inflater.inflate(R.layout.lista_usuarios, parent, false);

        }

        TextView username = (TextView) view.findViewById(R.id.text_username);

        //Configurar o textview para exibir os usuarios
        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return view;
    }
}
