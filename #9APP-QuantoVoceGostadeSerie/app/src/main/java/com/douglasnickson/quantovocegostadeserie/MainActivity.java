package com.douglasnickson.quantovocegostadeserie;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar avaliacao;
    private ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avaliacao = (SeekBar) findViewById(R.id.seekBarID);
        imagem = (ImageView) findViewById(R.id.imageExibicaoID);

        avaliacao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int valorProgresso = i;

                if(valorProgresso == 1 ){
                    imagem.setImageResource(R.drawable.pouco);
                }else if(valorProgresso == 2){
                    imagem.setImageResource(R.drawable.medio);
                }else if(valorProgresso == 3){
                    imagem.setImageResource(R.drawable.muito);
                }else if(valorProgresso == 4){
                    imagem.setImageResource(R.drawable.susto);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
