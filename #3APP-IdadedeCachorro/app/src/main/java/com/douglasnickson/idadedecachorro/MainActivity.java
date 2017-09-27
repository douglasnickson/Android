package com.douglasnickson.idadedecachorro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText caixaTexto;
    private Button botaoIdade;
    private TextView resultadoIdade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaTexto = (EditText) findViewById(R.id.caixa_textoID);
        botaoIdade = (Button) findViewById(R.id.botao_IdadeId);
        resultadoIdade = (TextView) findViewById(R.id.resultado_IdadeID);

        botaoIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperar o que foi Digitado
                String textoDigitado = caixaTexto.getText().toString();

                if (textoDigitado.isEmpty()){
                    //String Vazia (mensagem de erro)
                    resultadoIdade.setText("Nenhum Numero Digitado!");
                }else{
                    int valorDigitado = Integer.parseInt(textoDigitado);
                    int resultadoFinial = valorDigitado * 7;

                    resultadoIdade.setText("A idade do cachorro em anos humanos é: " + resultadoFinial + " anos ");
                }
            }
        });

    }
}
