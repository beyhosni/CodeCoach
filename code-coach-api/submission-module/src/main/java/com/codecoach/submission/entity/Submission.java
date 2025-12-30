package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité Submission - Représente une tentative de résolution d'exercice
 *
 * Workflow :
 * 1. Apprenant soumet du code
 * 2. Runner orchestre l'exécution sécurisée
 * 3. Tests s'exécutent dans le container
 * 4. SubmissionResult enregistre : pass/fail + erreurs
 * 5. Coach IA analyse les erreurs → hints socratiques
 *
 * Versionning : Chaque soumission est immuable (immutable log)
 */
@Entity
@Table(name = "cc_submission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status; // PENDING, SUCCESS, FAILED, ERROR

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum SubmissionStatus {
        PENDING,  // En attente d'exécution
        SUCCESS,  // Tests passent
        FAILED,   // Tests échouent
        ERROR     // Erreur de compilation ou runtime
    }
}
