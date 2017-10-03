package com.douglasnickson.a15app_whatsappclone.model;

import com.douglasnickson.a15app_whatsappclone.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by douglas-nickson on 02/10/17.
 */

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){

    }

    public void salvarDados(){
        DatabaseReference referenciaDatabase = ConfiguracaoFirebase.getFirebase();
        referenciaDatabase.child("Usuarios").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
