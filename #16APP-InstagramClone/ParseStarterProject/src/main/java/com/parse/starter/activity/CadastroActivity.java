package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseError;

public class CadastroActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText email;
    private EditText senha;
    private TextView abre_login;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuario = (EditText) findViewById(R.id.cadastro_usuario);
        email = (EditText) findViewById(R.id.cadastro_email);
        senha = (EditText) findViewById(R.id.cadastro_senha);
        abre_login = (TextView) findViewById(R.id.cadastro_msg);
        cadastrar = (Button) findViewById(R.id.bt_cadastro);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });

        abre_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirLoginUsuario();
            }
        });

    }

    private void cadastrarUsuario(){
        //Cria objeto usuario
        ParseUser user = new ParseUser();
        user.setUsername(usuario.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(senha.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){ //Sucesso ao salvar
                    Toast.makeText(CadastroActivity.this, "Cadastro Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                    abrirLoginUsuario();
                }else{ //Error ao salvar
                    ParseError parseError = new ParseError();
                    String erro = parseError.getErro(e.getCode());

                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
