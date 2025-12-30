package com.codecoach.content.repository;

import com.codecoach.shared.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByIsPublished(Boolean isPublished);
    List<Track> findByProgrammingLanguage(String programmingLanguage);
}
