package com.douglasnickson.a15app_whatsappclone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglasnickson.a15app_whatsappclone.Manifest;
import com.douglasnickson.a15app_whatsappclone.R;
import com.douglasnickson.a15app_whatsappclone.helper.Persmissao;
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
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Persmissao.validaPermissoes(1, this, permissoesNecessarias);

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
                String msgEnvio = "WhatsApp Codigo de Confirmacao" + token;

                //Salvar dados para Validacao
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                //Enviando SMS
                telefoneSemFormatacao = "5554";
                boolean enviadoSms = enviaSms("+" + telefoneSemFormatacao, msgEnvio);

                if (enviadoSms){
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente", Toast.LENGTH_LONG).show();
                }

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("TOKEN", "NOME:" + usuario.get("nome") + " FONE: "+ usuario.get("telefone") + " TOKEN: "+ usuario.get("token"));
            }
        });
    }

    private boolean enviaSms(String telefone, String mensagem){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null,null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        for (int resultado: grantResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidaoPermissao();
            }
        }
    }

    private void alertaValidaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissoes Negadas");
        builder.setMessage("Para utilizar esse app, e necessario aceitar as permissoes");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
