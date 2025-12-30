package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité SubmissionResult - Résultat détaillé de l'exécution d'une soumission
 *
 * Données enregistrées :
 * - Erreurs de compilation
 * - Erreurs d'exécution
 * - Tests échoués (quels tests, quel output)
 * - Métriques d'exécution (temps, mémoire)
 */
@Entity
@Table(name = "cc_submission_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "submission_id", nullable = false, unique = true)
    private Submission submission;

    @Column(columnDefinition = "TEXT")
    private String compilationError; // null si pas d'erreur

    @Column(columnDefinition = "TEXT")
    private String runtimeError; // null si pas d'erreur

    @Column(nullable = false)
    private Integer testsPassed;

    @Column(nullable = false)
    private Integer testsFailed;

    @Column(columnDefinition = "TEXT")
    private String failureDetails; // JSON ou texte détaillant les tests échoués

    @Column(nullable = false)
    private Long executionTimeMs;

    @Column(nullable = false)
    private Long memoryUsedMB;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
