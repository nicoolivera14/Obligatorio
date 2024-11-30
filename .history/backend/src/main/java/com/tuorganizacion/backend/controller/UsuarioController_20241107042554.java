package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.entity.Usuario;
import com.tuorganizacion.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/api/usuarios")
    public Usuario getUsuario(@RequestParam Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/usuarios/guardar")
    public String guardarUsuario(@RequestParam String nombre, @RequestParam String email) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuarioRepository.save(usuario);
        return "Usuario guardado";
    }
}
