package com.codecoach.auth.controller;

import com.codecoach.shared.dto.AuthRequestDto;
import com.codecoach.shared.dto.AuthResponseDto;
import com.codecoach.shared.dto.UserRegisterDto;
import com.codecoach.shared.entity.User;
import com.codecoach.auth.jwt.JwtTokenProvider;
import com.codecoach.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - Endpoints d'authentification
 *
 * Endpoints :
 * POST /api/v1/auth/register - Créer un compte
 * POST /api/v1/auth/login - Connexion (retourne JWT)
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Endpoint : POST /auth/register
     * Enregistre un nouvel utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegisterDto dto) {
        log.info("Registration attempt for email: {}", dto.getEmail());

        User user = userService.registerUser(dto);

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().toString());

        AuthResponseDto response = AuthResponseDto.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().toString())
                .userId(user.getId())
                .build();

        log.info("User registered successfully: {}", user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint : POST /auth/login
     * Authentifie un utilisateur et retourne un JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto dto) {
        log.info("Login attempt for email: {}", dto.getEmail());

        if (!userService.validateCredentials(dto.getEmail(), dto.getPassword())) {
            log.warn("Invalid credentials for email: {}", dto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe invalide");
        }

        User user = userService.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().toString());

        AuthResponseDto response = AuthResponseDto.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().toString())
                .userId(user.getId())
                .build();

        log.info("Login successful for user: {}", user.getId());
        return ResponseEntity.ok(response);
    }
}
