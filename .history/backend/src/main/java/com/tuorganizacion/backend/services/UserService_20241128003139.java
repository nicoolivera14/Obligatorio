package com.tuorganizacion.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuorganizacion.backend.model.Usuarios;
import com.tuorganizacion.persistence.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(String username, String password) {
        // Verifica si el usuario ya existe
        if (userRepository.findByUsername(username) != null) {
            return false; // El usuario ya existe
        }
        
        // Si no existe, crea el nuevo usuario
        Usuarios user = new Usuarios();
        user.setUsername(username);
        user.setPassword(password); // Asegúrate de hash la contraseña antes de guardarla
        userRepository.save(user);
        return true; // Usuario creado con éxito
    }    

    public boolean authenticate(String username, String password) {
        Usuarios user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public List<Usuarios> getAllUsers() {
        return userRepository.findAll();
    }

    public Usuarios findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Usuarios user) {
        userRepository.save(user);  // Si el usuario existe, será actualizado.
    }
}
