package com.codecoach.submission.repository;

import com.codecoach.shared.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByExerciseIdAndUserId(Long exerciseId, Long userId);
    List<Submission> findByUserId(Long userId);
    List<Submission> findByStatus(String status);
}
