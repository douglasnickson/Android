package com.douglasnickson.frasesdodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textoNovaFrase;
    private Button botaoNovaFrase;

    private String[] frases = {
            "\"Se você traçar metas absurdamente altas e falhar, seu fracasso será muito melhor que o sucesso de todos\" – James Cameron, cineasta",
            "\"O sucesso normalmente vem para quem está ocupado demais para procurar por ele\" – Henry David Thoreau, filósofo",
            " \"A vida é melhor para aqueles que fazem o possível para ter o melhor – John Wooden, jogador e treinador de basquete",
            "\"Os empreendedores falham, em média, 3,8 vezes antes do sucesso final. O que separa os bem-sucedidos dos outros é a persistência\" – Lisa M. Amos, executiva",
            "\"Se você não está disposto a arriscar, esteja disposto a uma vida comum\" – Jim Rohn, empreendedor"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNovaFrase = (TextView) findViewById(R.id.texto_fraseID);
        botaoNovaFrase = (Button) findViewById(R.id.botao_nova_fraseID);



        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int numero_aleatorio = random.nextInt(frases.length);
                textoNovaFrase.setText(frases[numero_aleatorio]);
            }
        });

    }
}
