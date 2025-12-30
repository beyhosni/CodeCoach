package com.codecoach.submission.service;

import com.codecoach.shared.dto.SubmissionDto;
import com.codecoach.shared.entity.Exercise;
import com.codecoach.shared.entity.Submission;
import com.codecoach.shared.entity.User;
import com.codecoach.submission.repository.SubmissionRepository;
import com.codecoach.content.repository.ExerciseRepository;
import com.codecoach.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SubmissionService - Gestion des soumissions de code
 *
 * Workflow :
 * 1. Valider l'exercice et l'utilisateur
 * 2. Créer une Submission en statut PENDING
 * 3. Envoyer vers le Runner (async, futur)
 * 4. SubmissionResult sera enregistré async
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    /**
     * Soumet du code pour un exercice
     */
    @Transactional
    public SubmissionDto submitCode(Long exerciseId, Long userId, String code) {
        log.info("Code submission for exercise: {} by user: {}", exerciseId, userId);

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}


        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé: " + exerciseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + userId));

        // Compter les tentatives précédentes
        int attemptNumber = submissionRepository.findByExerciseIdAndUserId(exerciseId, userId).size() + 1;

        Submission submission = Submission.builder()
                .exercise(exercise)
                .user(user)
                .code(code)
                .status(Submission.SubmissionStatus.PENDING.toString())
                .attemptNumber(attemptNumber)
                .build();

        Submission saved = submissionRepository.save(submission);
        log.info("Submission created: {} (attempt #{} for exercise {})", saved.getId(), attemptNumber, exerciseId);

        // TODO : Envoyer vers Runner Docker (via Kafka/Queue)
        // runnerService.executeAsync(saved.getId());

        return toDto(saved);
    }

    /**
     * Récupère l'historique des soumissions pour un utilisateur
     */
    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une soumission spécifique
     */
    @Transactional(readOnly = true)
    public SubmissionDto getSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée: " + submissionId));
        return toDto(submission);
    }

    /**
     * Convertit en DTO
     */
    private SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .exerciseId(submission.getExercise().getId())
                .userId(submission.getUser().getId())
                .code(submission.getCode())
                .status(submission.getStatus())
                .attemptNumber(submission.getAttemptNumber())
                .createdAt(submission.getCreatedAt())
                .build();
    }
}
