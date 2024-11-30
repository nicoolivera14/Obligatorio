package com.tuorganizacion.backend.services;
import com.tuorganizacion.backend.model.User;
import logic.ddaisobligatorio4.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Crear un nuevo usuario
    public boolean createUser(String username, String password) {
        // Verificar si el usuario ya existe
        if (userRepository.findByName(username).isPresent()) {
            return false; // Usuario ya existe
        }
        
        // Crear y guardar el nuevo usuario
        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password)); // Hashear la contraseña
        userRepository.save(user);
        return true;
    }

    // Autenticar usuario
    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findByName(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}
