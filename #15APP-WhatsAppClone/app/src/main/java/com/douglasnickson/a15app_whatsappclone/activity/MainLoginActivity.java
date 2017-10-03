package com.douglasnickson.a15app_whatsappclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.config.ConfiguracaoFirebase;
import com.douglasnickson.a15app_whatsappclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainLoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogin;
    private DatabaseReference referenciaFirebase;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        verificaUsuarioLogado();

        referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        email = (EditText) findViewById(R.id.edit_login_email);
        senha = (EditText) findViewById(R.id.edit_login_senha);
        botaoLogin = (Button) findViewById(R.id.bt_logar);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });
    }

    public void verificaUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    public void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(MainLoginActivity.this, "Logado com Sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainLoginActivity.this, "Erro ao fazer Login!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(MainLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void fazCadastroUsuario(View view){
        Intent intent = new Intent(MainLoginActivity.this, CadastrarActivity.class);
        startActivity(intent);
    }
}
