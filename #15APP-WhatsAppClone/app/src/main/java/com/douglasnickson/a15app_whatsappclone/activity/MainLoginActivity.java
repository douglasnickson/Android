package com.douglasnickson.a15app_whatsappclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.douglasnickson.a15app_whatsappclone.R;

public class MainLoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        email = (EditText) findViewById(R.id.edit_login_email);
        senha = (EditText) findViewById(R.id.edit_login_senha);
        botaoLogin = (Button) findViewById(R.id.bt_logar);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void fazCadastroUsuario(){
        Intent intent = new Intent(MainLoginActivity.this, CadastrarActivity.class);
        startActivity(intent);
    }
}
