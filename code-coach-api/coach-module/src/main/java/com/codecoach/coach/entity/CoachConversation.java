package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité CoachConversation - Thread de conversation avec l'IA socratique
 *
 * Lié à une Submission échouée.
 * L'IA pose des questions → hints → explications graduelles
 * Jamais de solution directe !
 */
@Entity
@Table(name = "cc_coach_conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "submission_id", nullable = false, unique = true)
    private Submission submission;

    @Column(nullable = false)
    private Integer hintLevel; // 1 = question, 2 = indice, 3 = pseudo-code, 4 = explication

    @Column(columnDefinition = "TEXT")
    private String context; // Contexte JSON de la conversation

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
