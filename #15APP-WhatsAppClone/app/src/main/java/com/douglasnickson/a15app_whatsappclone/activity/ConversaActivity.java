package com.douglasnickson.a15app_whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.adapter.MensagemAdapter;
import com.douglasnickson.a15app_whatsappclone.config.ConfiguracaoFirebase;
import com.douglasnickson.a15app_whatsappclone.helper.Base64Custom;
import com.douglasnickson.a15app_whatsappclone.helper.Preferencias;
import com.douglasnickson.a15app_whatsappclone.model.Conversa;
import com.douglasnickson.a15app_whatsappclone.model.Mensagem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ListView listview;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMsg;

    //dados do destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //dados do rementente
    private String idUsuarioRementente;
    private String nomeUsuarioRemetente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_msg);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
        listview = (ListView) findViewById(R.id.lv_conversas);

        //dados do usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRementente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();

        //recuperar dados passados
        Bundle extra = getIntent().getExtras();

        if(extra != null){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(emailDestinatario);
        }

        //Configurar Toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // Montar listview e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listview.setAdapter(adapter);

        //Recuperar mensagens do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("Mensagens")
                .child(idUsuarioRementente)
                .child(idUsuarioDestinatario);

        //Cria listener para mensagens
        valueEventListenerMsg = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // limpar mensagens
                mensagens.clear();

                // recuperar mensagens
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMsg);

        // Enviar mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoMensagem = editMensagem.getText().toString();

                if(textoMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar!", Toast.LENGTH_SHORT).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRementente);
                    mensagem.setMensagem(textoMensagem);

                    //Salvamos mensagem para o rementente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRementente, idUsuarioDestinatario, mensagem);
                    if(!retornoMensagemRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar mensagem, tente novamente!", Toast.LENGTH_SHORT).show();
                    }else{
                        //Salvamos mensagem para o destinatario
                        Boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioRementente, mensagem);
                        if(!retornoMensagemDestinatario){
                            Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem para o destinatario, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //Salvamos conversa para o remetente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioDestinatario);
                    conversa.setNome(nomeUsuarioDestinatario);
                    conversa.setMensagem(textoMensagem);
                    boolean retornoConversaRemetente = salvarConversa(idUsuarioRementente, idUsuarioDestinatario, conversa);
                    if(!retornoConversaRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente!", Toast.LENGTH_SHORT).show();
                    }else{
                        //Salvamos conversa para o destinatario
                        conversa = new Conversa();
                        conversa.setIdUsuario(idUsuarioRementente);
                        conversa.setNome(nomeUsuarioDestinatario);
                        conversa.setMensagem(textoMensagem);
                        boolean retornoConversaDestinatario = salvarConversa(idUsuarioRementente, idUsuarioDestinatario, conversa);
                        if(!retornoConversaDestinatario){
                            Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa para o destinatario, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }




                    editMensagem.setText("");
                }

            }
        });

    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){

        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("Mensagens");
            firebase.child(idRemetente).child(idDestinatario).push().setValue(mensagem);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){

        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("Conversas");
            firebase.child(idRemetente).child(idDestinatario).setValue(conversa);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMsg);
    }
}
