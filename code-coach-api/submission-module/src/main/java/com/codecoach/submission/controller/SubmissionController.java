package com.codecoach.submission.controller;

import com.codecoach.shared.dto.SubmissionDto;
import com.codecoach.submission.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * SubmissionController - API pour soumettre et tracker des soumissions
 *
 * Endpoints :
 * POST /api/v1/submissions/{exerciseId} - Soumettre du code
 * GET /api/v1/submissions - Historique de l'utilisateur
 * GET /api/v1/submissions/{submissionId} - Détails d'une soumission
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('LEARNER')")
public class SubmissionController {

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}

    private final SubmissionService submissionService;

    /**
     * POST /submissions/{exerciseId}
     * Soumet du code pour un exercice
     */
    @PostMapping("/{exerciseId}")
    public ResponseEntity<SubmissionDto> submitCode(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, String> payload) {

        String code = payload.get("code");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("User {} submitting code for exercise: {}", username, exerciseId);

        SubmissionDto submission = submissionService.submitCode(exerciseId, userId, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    /**
     * GET /submissions
     * Récupère l'historique des soumissions de l'utilisateur
     */
    @GetMapping
    public ResponseEntity<List<SubmissionDto>> getMySubmissions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // TODO : Récupérer le userId depuis le token ou DB
        Long userId = 1L; // Placeholder

        log.info("Fetching submissions for user: {}", username);
        List<SubmissionDto> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }

    /**
     * GET /submissions/{submissionId}
     * Récupère les détails d'une soumission
     */
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getSubmission(@PathVariable Long submissionId) {
        log.info("Fetching submission: {}", submissionId);
        SubmissionDto submission = submissionService.getSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}
