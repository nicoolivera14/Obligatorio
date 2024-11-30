import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SalaJuegoService {
    private final Map<String, SalaJuego> salas = new HashMap<>(); // Almacena las sesiones activas

    public SalaJuego crearSesion(List<Pregunta> preguntas) {
        String idSala = UUID.randomUUID().toString(); // Genera un ID único
        SalaJuego nuevaSesion = new SalaJuego(idSala, preguntas);
        salas.put(idSala, nuevaSala);
        return nuevaSala;
    }

    public SalaJuego obtenerSala(String idSala) {
        return salas.get(idSala);
    }

    public boolean unirseASala(String idSala, String jugador) {
        SalaJuego sala = salas.get(idSala);
        if (sala != null && !sala.getJugadores().contains(jugador)) {
            sala.getJugadores().add(jugador);
            return true;
        }
        return false;
    }
}
