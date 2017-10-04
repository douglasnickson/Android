package com.douglasnickson.a15app_whatsappclone.activity;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastrarActivity extends AppCompatActivity {

    private EditText email;
    private EditText nome;
    private EditText senha;
    private Button cadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        nome = (EditText) findViewById(R.id.edit_cadastro_nome);
        email = (EditText) findViewById(R.id.edit_cadastro_email);
        senha = (EditText) findViewById(R.id.edit_cadastro_senha);
        cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastrarActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastrarActivity.this, "Cadastro Realizado com Sucesso", Toast.LENGTH_LONG).show();
                    FirebaseUser userFirebase = task.getResult().getUser();
                    usuario.setId(userFirebase.getUid());
                    usuario.salvarDados();

                    autenticacao.signOut();
                    finish();
                }else{
                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite um Senha mais Forte!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O E-mail digitado nao e valido";
                    }catch(FirebaseAuthUserCollisionException e){
                        erroExcecao = "O E-mail digitado ja Existe!";
                    }catch (Exception e) {
                        erroExcecao = "Ao Cadastrar usuario";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastrarActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
