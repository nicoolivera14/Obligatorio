import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaJuegoController {

    @Autowired
    private PreguntaService preguntaService; // Servicio para obtener preguntas
    @Autowired
    private SalaJuegoService sesionJuegoService;

    @PostMapping("/crear")
    public ResponseEntity<SalaJuego> crearSesion() {
        try {
            List<Pregunta> preguntas = preguntaService.obtenerPreguntasDeAPI();
            SalaJuego nuevaSala = salaJuegoService.crearSala(preguntas);
            return ResponseEntity.ok(nuevaSala);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/{idSala}/unirse")
    public ResponseEntity<String> unirseASesion(@PathVariable String idSala, @RequestParam String jugador) {
        boolean unido = salaJuegoService.unirseASala(idSala, jugador);
        if (unido) {
            return ResponseEntity.ok("Jugador unido a la sala.");
        } else {
            return ResponseEntity.status(404).body("Sala no encontrada o jugador ya está en la sala.");
        }
    }

    @GetMapping("/{idSala}/pregunta")
    public ResponseEntity<Pregunta> obtenerPregunta(@PathVariable String idSala) {
        SalaJuego sesion = salaJuegoService.obtenerSesion(idSala);
        if (sala != null) {
            Pregunta pregunta = sala.getPreguntas().get(sala.getPreguntaActual());
            return ResponseEntity.ok(pregunta);
        }
        return ResponseEntity.status(404).body(null);
    }
}
