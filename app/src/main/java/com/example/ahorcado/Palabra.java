package com.example.ahorcado;

public class Palabra {
    private String palabra;
    private String tema;

    public Palabra(String palabra, String tema) {
        this.palabra = palabra;
        this.tema = tema;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
