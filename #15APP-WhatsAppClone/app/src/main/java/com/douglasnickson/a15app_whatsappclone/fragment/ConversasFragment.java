package com.douglasnickson.a15app_whatsappclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.activity.ConversaActivity;
import com.douglasnickson.a15app_whatsappclone.adapter.ContatoAdapter;
import com.douglasnickson.a15app_whatsappclone.adapter.ConversaAdapter;
import com.douglasnickson.a15app_whatsappclone.config.ConfiguracaoFirebase;
import com.douglasnickson.a15app_whatsappclone.helper.Base64Custom;
import com.douglasnickson.a15app_whatsappclone.helper.Preferencias;
import com.douglasnickson.a15app_whatsappclone.model.Contato;
import com.douglasnickson.a15app_whatsappclone.model.Conversa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerConversas;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        listView = (ListView) view.findViewById(R.id.lv_conversas);

        conversas = new ArrayList<>();

        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);


        //Recuperar Contato do Firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUserLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFirebase.getFirebase().child("Conversas").child(identificadorUserLogado);

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpar lista
                conversas.clear();

                //Lista contatos
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Conversa conversa = dados.getValue( Conversa.class );
                    conversas.add(conversa);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversa conversa = conversas.get(i);
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //Enviando dados para conversa activity
                intent.putExtra("nome", conversa.getNome());
                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
                intent.putExtra("email", email);

                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversas);
    }
}
