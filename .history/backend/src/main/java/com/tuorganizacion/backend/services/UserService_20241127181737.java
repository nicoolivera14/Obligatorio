package com.tuorganizacion.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuorganizacion.backend.model.User;
import com.tuorganizacion.persistence.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(String name, String password) {
        // Verifica si el usuario ya existe
        if (userRepository.findByName(name) != null) {
            return false; // El usuario ya existe
        }
        
        // Si no existe, crea el nuevo usuario
        User user = new User();
        user.setName(name);
        user.setPassword(password); // Asegúrate de hash la contraseña antes de guardarla
        userRepository.save(user);
        return true; // Usuario creado con éxito
    }    

    public boolean authenticate(String name, String password) {
        User user = userRepository.findByName(name);
        return user != null && user.getPassword().equals(password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);  // Si el usuario existe, será actualizado.
    }
}
