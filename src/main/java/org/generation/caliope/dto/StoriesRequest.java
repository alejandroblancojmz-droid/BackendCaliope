package org.generation.caliope.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record StoriesRequest(
        String title,
        String description,
        String status,
        LocalDate created_date,
        LocalDate published_date,
        List<Long> genres,
        MultipartFile picture_front_pages,  // Cambiado a MultipartFile
        MultipartFile file_pdf
) {
}
