package com.codecoach.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRegisterDto - Payload pour l'inscription
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {

    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "Username requis")
    @Size(min = 3, max = 50, message = "Username entre 3 et 50 caractères")
    private String username;

    @NotBlank(message = "Mot de passe requis")
    @Size(min = 8, message = "Mot de passe minimum 8 caractères")
    private String password;

    private String firstName;

    private String lastName;
}
