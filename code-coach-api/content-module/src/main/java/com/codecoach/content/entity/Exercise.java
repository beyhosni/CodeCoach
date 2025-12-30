package com.codecoach.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité Exercise - Exercice de code individualisé
 *
 * Principes pédagogiques :
 * - Chaque exercice a des tests d'acceptation clairement définis
 * - Les erreurs de test incluent des hints socratiques (pas la solution)
 * - Progression tracée par type d'erreur (compilation, logique, tests)
 *
 * Exemple d'exercice :
 * Titre: "Écrire une méthode isEven"
 * Description: "Implémenter une méthode qui retourne true si le nombre est pair"
 * Tests: "isEven(4) == true, isEven(3) == false"
 */
@Entity
@Table(name = "cc_exercise")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String starterCode; // Code pré-rempli pour l'apprenant

    @Column(columnDefinition = "TEXT", nullable = false)
    private String testCode; // Tests JUnit à faire passer

    @Column(nullable = false)
    private Integer orderInTrack; // Ordre séquentiel dans la track

    @Column(nullable = false)
    private Integer difficulty; // 1-5

    /**
     * Paramètres de sécurité pour l'exécution
     */
    @Column(nullable = false)
    private Long timeoutMillis; // Timeout d'exécution

    @Column(nullable = false)
    private Long memoryLimitMB;

    @Column(nullable = false)
    private Boolean isPublished;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        timeoutMillis = 5000L; // Par défaut 5 secondes
        memoryLimitMB = 256L;  // Par défaut 256 MB
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
