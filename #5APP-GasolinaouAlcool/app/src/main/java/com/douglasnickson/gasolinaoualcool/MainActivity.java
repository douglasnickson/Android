package com.douglasnickson.gasolinaoualcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText valorAlcool;
    private EditText valorGasolina;
    private Button botaoResultado;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valorAlcool = (EditText) findViewById(R.id.valor_alcoolID);
        valorGasolina = (EditText) findViewById(R.id.valor_gasolinaID);
        botaoResultado = (Button) findViewById(R.id.botao_ID);
        textoResultado = (TextView) findViewById(R.id.resultadoID);

        botaoResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recupera Valores Digitados
                String vAlcool = valorAlcool.getText().toString();
                String vGasolina = valorGasolina.getText().toString();

                //Convertendo para numero
                Double precoAlcool = Double.parseDouble(vAlcool);
                Double precoGasolina = Double.parseDouble(vGasolina);

                double resultado = precoAlcool / precoGasolina;

                if(resultado >= 0.7){
                    //Gasolina
                    textoResultado.setText("É melhor utilizar Gasolina");
                }else{
                    //Alcool
                    textoResultado.setText("É melhor utilizar Álcool");
                }

            }
        });
    }
}
