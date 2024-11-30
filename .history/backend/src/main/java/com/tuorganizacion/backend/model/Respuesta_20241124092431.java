package com.tuorganizacion.backend.model;

public class Respuesta {
    private int id;
    private int idPregunta;
    private int idUsuario;
    private int opcion;
    
    public int getId() {
        return id;
    }
    public int getIdPregunta() {
        return idPregunta;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public int getOpcion() {
        return opcion;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
}
