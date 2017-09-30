package com.douglasnickson.a15app_whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codArea;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome        = (EditText) findViewById(R.id.nomeId);
        telefone    = (EditText) findViewById(R.id.edit_telefone);
        codPais     = (EditText) findViewById(R.id.edit_codPais);
        codArea     = (EditText) findViewById(R.id.edit_codArea);
        cadastrar   = (Button) findViewById(R.id.bt_cadastrar);

        //Cria as Mascaras para os Numeros
        SimpleMaskFormatter simpleMaskTelefone  = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter simpleMaskCodPais   = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea   = new SimpleMaskFormatter("NN");

        MaskTextWatcher maskTelefone    = new MaskTextWatcher(telefone, simpleMaskTelefone);
        MaskTextWatcher maskPais        = new MaskTextWatcher(codPais, simpleMaskCodPais);
        MaskTextWatcher maskArea        = new MaskTextWatcher(codArea, simpleMaskCodArea);

        telefone.addTextChangedListener(maskTelefone);
        codArea.addTextChangedListener(maskArea);
        codPais.addTextChangedListener(maskPais);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto = codPais.getText().toString() + codArea.getText().toString() + telefone.getText().toString();
                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                //Gerar Token
                Random random = new Random();
                int numero_random = random.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numero_random);

                //Salvar dados para Validacao
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
            }
        });
    }
}
