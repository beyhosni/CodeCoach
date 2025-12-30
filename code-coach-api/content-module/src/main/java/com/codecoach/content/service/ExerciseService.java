package com.codecoach.content.service;

import com.codecoach.shared.dto.ExerciseDto;
import com.codecoach.shared.entity.Exercise;
import com.codecoach.shared.entity.Track;
import com.codecoach.content.repository.ExerciseRepository;
import com.codecoach.content.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ExerciseService - Gestion des exercices
 *
 * Responsabilités :
 * - Récupération d'exercices (curriculum)
 * - Validation de l'ordre progressif
 * - Pas de génération de solutions !
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final TrackRepository trackRepository;

    /**
     * Récupère tous les exercices d'une track publiée
     */
    @Transactional(readOnly = true)
    public List<ExerciseDto> getExercisesByTrack(Long trackId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track non trouvée: " + trackId));

        return exerciseRepository.findByTrackIdAndIsPublished(trackId, true)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un exercice spécifique
     */
    @Transactional(readOnly = true)
    public ExerciseDto getExercise(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        return toDto(exercise);
    }

    /**
     * Convertit une entité en DTO
     * NOTE : Ne expose PAS le test_code à l'apprenant !
     */
    private ExerciseDto toDto(Exercise exercise) {
        return ExerciseDto.builder()
                .id(exercise.getId())
                .trackId(exercise.getTrack().getId())
                .title(exercise.getTitle())
                .description(exercise.getDescription())
                .starterCode(exercise.getStarterCode())
                .difficulty(exercise.getDifficulty())
                .createdAt(exercise.getCreatedAt())
                .build();
    }
}
