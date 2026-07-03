package org.generation.caliope.repository;

import org.generation.caliope.model.Stories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoriesRepository extends JpaRepository<Stories, Long> {
}
