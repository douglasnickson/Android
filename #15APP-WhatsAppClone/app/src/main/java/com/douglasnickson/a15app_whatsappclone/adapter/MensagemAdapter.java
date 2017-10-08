package com.douglasnickson.a15app_whatsappclone.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.helper.Preferencias;
import com.douglasnickson.a15app_whatsappclone.model.Mensagem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DouglasNickson on 06/10/2017.
 */

public class MensagemAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context c, ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        //Verifica se a lista foi preenchida
        if(mensagens != null){

            //Recuperar dados do usuario rementente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRementente = preferencias.getIdentificador();

            //Inicializa o objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Recuperar mensagem
            Mensagem mensagem = mensagens.get(position);

            //Monta a partir do xml
            if (idUsuarioRementente.equals(mensagem.getIdUsuario())) {
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }
            //Recuperar elemento para exibicao
            TextView textoMensagem = view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }

        return view;
    }
}
