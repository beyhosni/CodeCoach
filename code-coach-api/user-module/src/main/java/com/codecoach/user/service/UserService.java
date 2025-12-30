package com.codecoach.user.service;

import com.codecoach.shared.dto.UserDto;
import com.codecoach.shared.dto.UserRegisterDto;
import com.codecoach.shared.entity.User;
import com.codecoach.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserService - Gestion des utilisateurs
 *
 * Responsabilités :
 * - Création/suppression de comptes
 * - Validation d'unicité email/username
 * - Hachage sécurisé des mots de passe
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Enregistre un nouvel utilisateur
     */
    @Transactional
    public User registerUser(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email déjà enregistré: " + dto.getEmail());
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username déjà pris: " + dto.getUsername());
        }

        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(User.UserRole.LEARNER) // Par défaut apprenti
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur par email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Valide les credentials
     */
    public boolean validateCredentials(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .orElse(false);
    }

    /**
     * Convertit User en DTO public
     */
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
