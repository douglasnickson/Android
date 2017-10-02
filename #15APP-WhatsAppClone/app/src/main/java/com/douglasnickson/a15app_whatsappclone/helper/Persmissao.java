package com.douglasnickson.a15app_whatsappclone.helper;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by douglas-nickson on 01/10/17.
 */

public class Persmissao {

    public static boolean validaPermissoes(int requestCode, AppCompatActivity activity, String[] permissoes){

        List<String> listaPermissoes = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= 23){

            //Percorre as permissoes passadas
            for (String persmissao: permissoes){
               boolean validaPermissao =  ContextCompat.checkSelfPermission(activity, persmissao) == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) listaPermissoes.add(persmissao);
            }

            //Caso a lista esteja vazia nao e necessaria solicitar permissao
            if(listaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicita Permissao
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }

        return true;
    }
}
