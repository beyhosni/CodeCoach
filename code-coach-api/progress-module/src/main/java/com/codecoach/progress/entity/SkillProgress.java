package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité SkillProgress - Suivi de progression par skill/concept
 *
 * Métrique :
 * - successfulAttempts : tentatives réussies
 * - totalAttempts : tentatives totales
 * - mastered : true si >= 2 succès consécutifs
 * - lastAttemptAt : dernière tentative
 *
 * Objectif : Gamifier l'apprentissage sans donner d'illusion
 */
@Entity
@Table(name = "cc_skill_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Integer successfulAttempts;

    @Column(nullable = false)
    private Integer totalAttempts;

    @Column(nullable = false)
    private Boolean isMastered;

    @Column(name = "last_attempt_at")
    private LocalDateTime lastAttemptAt;

    @Column(name = "mastered_at")
    private LocalDateTime masteredAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        successfulAttempts = 0;
        totalAttempts = 0;
        isMastered = false;
    }
}
