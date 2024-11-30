package com.tuorganizacion.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.model.Usuario;

@Service
public class SalaJuegoService {
    private final Map<String, SalaJuego> salas = new HashMap<>(); // Almacena las salas activas

    public SalaJuego crearSalaJuego(List<Pregunta> preguntas) {
        String idSala = UUID.randomUUID().toString(); // Genera un ID único
        SalaJuego nuevaSala = new SalaJuego(idSala, preguntas);
        salas.put(idSala, nuevaSala);
        return nuevaSala;
    }

    public SalaJuego obtenerSalaJuego(String idSala) {
        return salas.get(idSala);
    }

    public boolean unirseASalaJuego(String idSala, Usuario jugador) {
        SalaJuego sala = salas.get(idSala);
        if (sala != null && !sala.getJugadores().contains(jugador)) {
            sala.getJugadores().add(jugador);
            return true;
        }
        return false;
    }
    public Pregunta obtenerPreguntaNoRespondida(String idSala) {
        SalaJuego sala = obtenerSalaJuego(idSala);
        if (sala != null) {
            for (Pregunta pregunta : sala.getPreguntas()) {
                if (!pregunta.isRespondida()) {
                    return pregunta;
                }
            }
        }
        return null; 
    }
    public boolean marcarPreguntaComoRespondida(String idSala, int idPregunta) {
        SalaJuego sala = salas.get(idSala);
    
        if (sala != null  && idPregunta >= 0 && idPregunta < sala.getPreguntas().size()) {
            Pregunta pregunta = sala.getPreguntas().get(idPregunta);
    
            if (!pregunta.isRespondida()) {
                pregunta.setRespondida(true);
                return true;
            }
        }
    
        return false;
    }
    
}
