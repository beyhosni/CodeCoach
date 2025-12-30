package com.codecoach.content.repository;

import com.codecoach.shared.entity.Exercise;
import com.codecoach.shared.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByTrack(Track track);
    List<Exercise> findByTrackIdAndIsPublished(Long trackId, Boolean isPublished);
}
