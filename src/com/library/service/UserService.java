package com.library.service;

import com.library.entity.User;
import com.library.repository.UserRepository;

import java.io.IOException;
import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user
    public void createUser(User user) throws IOException, ClassNotFoundException {
        userRepository.save(user);
        System.out.println("Le livre " + user.getName() + " est ajouté");
    }

    // Find a user by id
    public User getUserById(int id) throws IOException, ClassNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

    // Get all users
    public List<User> getAllusers() throws IOException, ClassNotFoundException {
        return userRepository.findAll();
    }

    // Update an existing user
    public void updateUser(User user) throws IOException, ClassNotFoundException {
        userRepository.update(user);
        System.out.println("Le livre " + user.getName() + " est edité");
    }

    // Delete a user by id
    public boolean deleteUser(int id) throws IOException, ClassNotFoundException {
        return userRepository.deleteById(id);
    }
}
