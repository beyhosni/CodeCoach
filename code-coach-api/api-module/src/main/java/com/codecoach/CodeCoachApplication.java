package com.codecoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * CodeCoachApplication - Point d'entrée Spring Boot
 *
 * Modules chargés automatiquement :
 * - auth-module (JWT, Security)
 * - user-module (CRUD utilisateurs)
 * - content-module (Tracks, Exercices)
 * - submission-module (Soumissions, versioning)
 * - coach-module (IA socratique)
 * - progress-module (Tracking)
 * - runner-module (Orchestration exécution)
 *
 * Configuration :
 * - PostgreSQL via Flyway
 * - Redis pour cache/rate-limiting
 * - Kafka pour événements async (placeholder)
 * - WebSocket pour feedback live
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class CodeCoachApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeCoachApplication.class, args);
    }
}
