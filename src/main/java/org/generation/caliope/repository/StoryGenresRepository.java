package org.generation.caliope.repository;

import org.generation.caliope.model.StoryGenres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryGenresRepository extends JpaRepository<StoryGenres, Long> {
    List<StoryGenres> findByStoryId(Long storyId);
}
