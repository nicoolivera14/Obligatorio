package com.tuorganizacion.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pregunta {
    private int idPregunta;
    private String pregunta;
    private List<String> opciones;
    private boolean respondida = false;
    
    @JsonIgnore
    private String respuestaCorrecta;

    // Getters y setters
    public int getIdPregunta() {
        return id;
    }
    public void setIdPregunta(int idPregunta) {
        this.id = id;
    }
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }
    public boolean isRespondida() {
        return respondida;
    }
}
