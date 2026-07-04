package org.generation.caliope.dto;

import java.time.LocalDateTime;

public record LibraryResponse(Long id, Long userId, Long storyId, LocalDateTime savedAt) {


}
