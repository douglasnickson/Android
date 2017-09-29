package com.douglasnickson.somdosbichos;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView cao;
    private ImageView gato;
    private ImageView leao;
    private ImageView macaco;
    private ImageView ovelha;
    private ImageView vaca;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cao = (ImageView) findViewById(R.id.caoID);
        gato = (ImageView) findViewById(R.id.gatoID);
        leao = (ImageView) findViewById(R.id.leaoID);
        macaco = (ImageView) findViewById(R.id.macacoID);
        ovelha = (ImageView) findViewById(R.id.ovelhaID);
        vaca = (ImageView) findViewById(R.id.vacaID);

        cao.setOnClickListener(this);
        gato.setOnClickListener(this);
        leao.setOnClickListener(this);
        macaco.setOnClickListener(this);
        ovelha.setOnClickListener(this);
        vaca.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.caoID:
                mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.cao);
                tocarSom();
                break;

            case R.id.gatoID:
                mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.gato);
                tocarSom();
                break;

            case R.id.leaoID:
                mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.leao);
                tocarSom();
                break;

            case R.id.macacoID:
                mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.macaco);
                tocarSom();
                break;

            case R.id.ovelhaID:
                mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.ovelha);
                tocarSom();
                break;

            case R.id.vacaID:
                mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.vaca);
                tocarSom();
                break;
        }
    }

    public void tocarSom(){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
