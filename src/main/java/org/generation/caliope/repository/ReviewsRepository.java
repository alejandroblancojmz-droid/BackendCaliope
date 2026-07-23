package org.generation.caliope.repository;

import org.generation.caliope.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    List<Reviews> findByStories_Id(Long storiesId);

    boolean existsByStories_IdAndUsers_Id(Long storiesId, Long usersId);
}