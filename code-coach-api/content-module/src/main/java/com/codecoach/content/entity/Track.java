package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité Track - Représente une piste d'apprentissage (chemin pédagogique)
 *
 * Exemple : "Java Fundamentals", "OOP Mastery", "Web Development Basics"
 * Chaque track contient plusieurs exercices progressifs
 */
@Entity
@Table(name = "cc_track")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String programmingLanguage; // "java", "python", etc.

    @Column(nullable = false)
    private Integer difficulty; // 1 (facile) à 5 (expert)

    @Column(nullable = false)
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

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
