package com.parse.starter.util;

import android.content.Intent;

import java.util.HashMap;

/**
 * Created by douglas-nickson on 08/10/17.
 */

public class ParseError {

    private HashMap<Integer, String> erros;

    public ParseError() {
        this.erros = new HashMap<>();
        this.erros.put(201, "A Senha nao foi preenchida!");
        this.erros.put(202, "Usuario ja existe, escolha outro usuario!");
    }

    public String getErro(int codErro){
        return this.erros.get(codErro);
    }
}
