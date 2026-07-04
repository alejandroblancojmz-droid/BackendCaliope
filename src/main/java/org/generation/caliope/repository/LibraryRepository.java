package org.generation.caliope.repository;

import org.generation.caliope.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> findByUsersId(Long userId);

    Optional<Library> findByUsersIdAndStoriesId(Long userId, Long storyId);

    boolean existsByUsersIdAndStoriesId(Long userId, Long storyId);

    void deleteByUsersIdAndStoriesId(Long userId, Long storyId);

    List<Library> findByStoriesId(Long storyId);

}
