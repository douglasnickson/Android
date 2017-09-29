package com.douglasnickson.a12app_blocodeanotacoes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String ARQUIVO_TEXT = "arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        botaoSalvar = (ImageView) findViewById(R.id.salvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Arquivo Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
        if (lerArquivo() != null){
            texto.setText(lerArquivo());
        }
    }

    private void gravarNoArquivo (String texto){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("ARQUIVO_TXT", Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();
        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo(){
        String resultado = "";

        //Abrir o Arquivo
        try {
            InputStream arquivo = openFileInput(ARQUIVO_TEXT);
            //ler o arquivo
            if(arquivo != null){
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);
                //Gerar o buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //recuperar o texto do arquivo
                String linhaArquivo = "";

                while((linhaArquivo = bufferedReader.readLine()) != null){
                    resultado += linhaArquivo;
                }
                arquivo.close();
            }

        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

        return resultado;
    }
}
