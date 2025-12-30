package com.codecoach.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SubmissionResultDto - Résultat d'exécution d'une soumission
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResultDto {
    private Long id;
    private Long submissionId;
    private String compilationError;
    private String runtimeError;
    private Integer testsPassed;
    private Integer testsFailed;
    private String failureDetails;
    private Long executionTimeMs;
    private Long memoryUsedMB;
    private LocalDateTime createdAt;
}
