package com.tuorganizacion.backend.model;

public class PreguntaOpciones {
    private int id;
    private int idPregunta;
    private String texto;

    public int getId() {
        return id;
    }
    public int getIdPregunta() {
        return idPregunta;
    }
    public String getTexto() {
        return texto;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
