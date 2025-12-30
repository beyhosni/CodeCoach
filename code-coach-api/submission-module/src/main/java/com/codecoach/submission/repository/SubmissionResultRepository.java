package com.codecoach.submission.repository;

import com.codecoach.shared.entity.SubmissionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionResultRepository extends JpaRepository<SubmissionResult, Long> {
    Optional<SubmissionResult> findBySubmissionId(Long submissionId);
}
