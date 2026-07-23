package org.generation.caliope.dto;
import java.time.LocalDateTime;

public record ReviewsRequest(Long id, Integer rate,
                             String review, LocalDateTime creation) {
}
