package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private TextView abre_cadastro;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verificar Usuario Logado
        verificarUsuarioLogado();

        usuario = (EditText) findViewById(R.id.login_usuario);
        senha = (EditText) findViewById(R.id.login_senha);
        abre_cadastro = (TextView) findViewById(R.id.login_msg);
        login = (Button) findViewById(R.id.login_bt);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLogin = usuario.getText().toString();
                String userSenha = senha.getText().toString();
                verificaLogin(userLogin, userSenha);
            }
        });

        abre_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCadastroUsuario();
            }
        });
    }

    private void verificaLogin(String login, String senha){
        ParseUser.logInInBackground(login, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Toast.makeText(LoginActivity.this, "Login Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();
                }else{
                    Toast.makeText(LoginActivity.this, "Erro ao fazer Login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirCadastroUsuario(){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity( intent );
    }

    public void verificarUsuarioLogado(){
        if (ParseUser.getCurrentUser() != null){
            //Envia o usario para a tela principal
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent );
        finish();
    }
}
