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

    public SalaJuego crearSalaJuego(List<Pregunta> preguntas, List<Usuario> jugadores) {
        String idSala = UUID.randomUUID().toString(); // Genera un ID único
        SalaJuego nuevaSala = new SalaJuego(idSala, preguntas, );
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
    public boolean limpiarSalaSiTermino(String idSala) {
        SalaJuego sala = salas.get(idSala);
    
        // Verifica si la sala existe y si ya no hay preguntas pendientes
        if (sala != null && sala.getPreguntas().stream().allMatch(Pregunta::isRespondida)) {
            // Elimina la sala de las salas activas
            sala.getJugadores().clear();
            salas.remove(idSala);
            return true; // La sala fue limpiada correctamente
        }
        
        return false; // La sala no fue limpiada, ya que aún hay preguntas pendientes
    }

    private void iniciarTemporizadorDeInactividad(String idSala, Usuario jugador) {
        Timer timer = new Timer();
        temporizadoresDeSala.put(idSala, timer);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Si el jugador no ha interactuado en 90 segundos, cierra la sala
                cerrarSalaPorInactividad(idSala);
            }
        }, 90000); // 90 segundos
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
