package com.openclassrooms.mddapi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        return userRepository.save(user);
    }
    
    public User authenticateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)) // Ajoutez le hash si besoin
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
    
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}