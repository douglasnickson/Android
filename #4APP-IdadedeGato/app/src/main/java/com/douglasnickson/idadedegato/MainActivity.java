package com.douglasnickson.idadedegato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText numeroDigitado;
    private Button botao;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeroDigitado = (EditText) findViewById(R.id.caixa_idadeID);
        botao = (Button) findViewById(R.id.botaoID);
        resultado = (TextView) findViewById(R.id.resultado_textoID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperar o que foi digitado
                String numero = numeroDigitado.getText().toString();

                if (numero.isEmpty()){
                    resultado.setText("Digite um Numero!");
                }else{
                    int valorDigitado = Integer.parseInt(numero);
                    int resultadoFinal = valorDigitado * 15;
                    resultado.setText("A idade do seu gato em anos humanos e: " + resultadoFinal + " anos ");
                }
            }
        });

    }
}
