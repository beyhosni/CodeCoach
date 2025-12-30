package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité CoachMessage - Messages individuels dans une conversation coaching
 *
 * Type :
 * - QUESTION : Question socratique orientante
 * - HINT : Indice conceptuel
 * - PSEUDO_CODE : Pseudo-code partiel
 * - EXPLANATION : Explication d'erreur
 * - FEEDBACK : Feedback sur tentative
 */
@Entity
@Table(name = "cc_coach_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private CoachConversation conversation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer sequenceNumber; // Ordre dans la conversation

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum MessageType {
        QUESTION,      // "Qu'est-ce qu'une variable ?"
        HINT,          // "Pense au type de données..."
        PSEUDO_CODE,   // "if (condition) { ... }"
        EXPLANATION,   // "L'erreur vient de..."
        FEEDBACK       // "Tu t'approches !"
    }
}
