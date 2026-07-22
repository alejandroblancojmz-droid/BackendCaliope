package org.generation.caliope.repository;

import org.generation.caliope.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByStories_IdAndUsers_Id(Long storiesId, Long usersId);

    long countByStories_Id(Long storiesId);
}
