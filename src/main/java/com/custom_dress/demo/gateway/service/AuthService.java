package com.custom_dress.demo.gateway.service;

import com.custom_dress.demo.gateway.model.User;
import com.custom_dress.demo.gateway.repository.UserRepository;
import com.custom_dress.demo.utility.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Allow ROLE_ADMIN if explicitly requested, otherwise default to ROLE_USER
        if (!"ROLE_ADMIN".equals(user.getRole())) {
            user.setRole("ROLE_USER");
        }

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(email, user.getRole(), user.getId());
    }
}
