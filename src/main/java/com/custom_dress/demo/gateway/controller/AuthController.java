package com.custom_dress.demo.gateway.controller;

import com.custom_dress.demo.gateway.model.User;
import com.custom_dress.demo.gateway.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        return ResponseEntity.ok(Map.of("message", authService.register(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String token = authService.login(
                request.get("email"),
                request.get("password"));

        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(OAuth2AuthenticationToken authentication) {
        OAuth2User oauth2User = authentication.getPrincipal();
        // Extract user info
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        // Register or update user in DB, then generate JWT
        String token = authService.oauth2LoginOrRegister(email, name);
        // Optionally, redirect to frontend with token as query param
        // return ResponseEntity.status(302).header("Location", "http://localhost:5173/auth?token=" + token).build();
        return ResponseEntity.ok(Map.of("token", token));
    }
}
