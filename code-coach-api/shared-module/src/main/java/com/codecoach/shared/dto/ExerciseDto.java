package com.codecoach.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ExerciseDto - Représentation d'un exercice
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    private Long id;
    private Long trackId;
    private String title;
    private String description;
    private String starterCode;
    private Integer difficulty;
    private LocalDateTime createdAt;
}
