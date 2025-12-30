package com.codecoach.content.controller;

import com.codecoach.shared.dto.ExerciseDto;
import com.codecoach.content.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ExerciseController - API pour récupérer les exercices
 *
 * Endpoints :
 * GET /api/v1/exercises/{trackId} - Liste les exercices d'une track
 * GET /api/v1/exercises/{exerciseId}/details - Détails d'un exercice
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
@PreAuthorize("hasRole('LEARNER')")
public class ExerciseController {

    private final ExerciseService exerciseService;

    /**
     * GET /exercises/track/{trackId}
     * Récupère tous les exercices d'une track
     */
    @GetMapping("/track/{trackId}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByTrack(@PathVariable Long trackId) {
        log.info("Fetching exercises for track: {}", trackId);
        List<ExerciseDto> exercises = exerciseService.getExercisesByTrack(trackId);
        return ResponseEntity.ok(exercises);
    }

    /**
     * GET /exercises/{exerciseId}/details
     * Récupère les détails d'un exercice spécifique
     */
    @GetMapping("/{exerciseId}/details")
    public ResponseEntity<ExerciseDto> getExercise(@PathVariable Long exerciseId) {
        log.info("Fetching exercise: {}", exerciseId);
        ExerciseDto exercise = exerciseService.getExercise(exerciseId);
        return ResponseEntity.ok(exercise);
    }
}
