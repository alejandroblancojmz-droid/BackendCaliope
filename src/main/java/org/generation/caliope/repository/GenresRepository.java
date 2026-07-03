package org.generation.caliope.repository;

import org.generation.caliope.model.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<Genres, Long> {
}
