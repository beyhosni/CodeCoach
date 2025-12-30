package com.codecoach.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SubmissionDto - Payload pour soumettre du code
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDto {
    private Long id;
    private Long exerciseId;
    private Long userId;
    private String code;
    private String status;
    private Integer attemptNumber;
    private LocalDateTime createdAt;
}
