package com.tuorganizacion.backend.model;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

@Component
public class SalaJuegoStorage {
    private Map<String, SalaJuego> salas = new ConcurrentHashMap<>();

    public void agregarSala(String id, SalaJuego sala) {
        salas.put(id, sala);
    }

    public SalaJuego obtenerSala(String id) {
        return salas.get(id);
    }

    public List<SalaJuego> obtenerTodasLasSalas() {
        return List.copyOf(salas.values());
    }
}
