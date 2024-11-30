package com.tuorganizacion.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.model.Usuario;

@Service
public class SalaJuegoService {
    private final Map<String, SalaJuego> salas = new HashMap<>(); // Almacena las salas activas
    private final Map<String, Timer> temporizadoresDeSala = new HashMap<>(); // Para manejar el temporizador de inactividad de cada sala

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

            // Inicia un temporizador para el jugador que se unió a la sala
            iniciarTemporizadorDeInactividad(idSala, jugador);
            
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
    public void limpiarSalaSiTermino(String idSala) {
        SalaJuego sala = salas.get(idSala);
        if (sala != null) {
            // Verifica si ya no hay preguntas sin responder
            boolean todasRespondidas = sala.getPreguntas().stream()
                .allMatch(Pregunta::isRespondida);
            
            if (todasRespondidas) {
                // Vacia la sala (elimina jugadores y preguntas)
                sala.getJugadores().clear();
                sala.getPreguntas().clear();
                // Si no hay jugadores, eliminamos la sala
                if (sala.getJugadores().isEmpty()) {
                    salas.remove(idSala);
                }
            }
        }
    }

    // Método para iniciar un temporizador de inactividad de 45 segundos
    private void iniciarTemporizadorDeInactividad(String idSala, Usuario jugador) {
        Timer timer = new Timer();
        temporizadoresDeSala.put(idSala, timer);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Si el jugador no ha interactuado en 45 segundos, cierra la sala
                cerrarSalaPorInactividad(idSala);
            }
        }, 90000); // 45 segundos
    }

    // Método para vaciar y cerrar la sala si se detecta inactividad
    private void cerrarSalaPorInactividad(String idSala) {
        SalaJuego sala = salas.get(idSala);
        if (sala != null) {
            // Vacia la sala y elimina la entrada de la sala en el mapa
            sala.getJugadores().clear();
            sala.getPreguntas().clear();
            salas.remove(idSala);
            temporizadoresDeSala.remove(idSala);
        }
    }
    
}
