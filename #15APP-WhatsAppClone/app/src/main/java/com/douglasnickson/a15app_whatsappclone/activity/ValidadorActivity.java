package com.douglasnickson.a15app_whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.StringTokenizer;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = (EditText) findViewById(R.id.edit_codSms);
        validar = (Button) findViewById(R.id.bt_validar);

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(codigoValidacao, simpleMaskFormatter);

        codigoValidacao.addTextChangedListener(mascaraCodigoValidacao);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Token Validado com Sucesso", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Token Nao Validado com Sucesso", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
