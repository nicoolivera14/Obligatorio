package com.tuorganizacion.backend.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tuorganizacion.backend.model.User;

public class PersistenceController {
    private final UserJpaController userJpa;

    public PersistenceController(UserJpaController userJpa) {
        this.userJpa = userJpa;
    }

    public PersistenceController() {
        this(new UserJpaController());
    }

    public void addUser(User user) {
        if (user == null || user.getName() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User, name, or password cannot be null");
        }
        userJpa.create(user);
    }

    public void deleteUser(int id) throws NonexistentEntityException {
        userJpa.destroy(id);
    }

    public void editUser(User user) {
        try {
            userJpa.edit(user);
        } catch (NonexistentEntityException e) {
            Logger.getLogger(PersistenceController.class.getName())
                  .log(Level.SEVERE, "User with ID " + user.getId() + " not found", e);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName())
                  .log(Level.SEVERE, "An error occurred while editing the user", ex);
        }
    }

    public User findUser(int id) {
        return userJpa.findUser(id);
    }

    public List<User> getUsersList() {
        return userJpa.findUserEntities();
    }

    public User findUserByName(String name) {
        return userJpa.findUserEntities().stream()
                      .filter(user -> user.getName().equals(name))
                      .findFirst()
                      .orElse(null);
    }

    public boolean userExists(int id) {
        return findUser(id) != null;
    }
}
