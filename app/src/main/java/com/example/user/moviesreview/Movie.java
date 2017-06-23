package com.example.user.moviesreview;

/**
 * Created by user on 19/06/2017.
 */

public class Movie {

    public String imagem;
    public String titulo;
    public String data_public;
    public String link;
    public String sumario;

    public Movie(String imagem, String titulo , String sumario, String data_public, String link) {
        this.imagem = imagem;
        this.titulo = titulo;
        this.sumario = sumario;
        this.data_public = data_public;
        this.link = link;
    }

    @Override
    public String toString(){
        String tituloFilme = "";
        tituloFilme += "Title: " + this.titulo;

        return tituloFilme;
    }

}
