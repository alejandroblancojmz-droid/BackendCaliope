package org.generation.caliope.dto;

import java.time.LocalDate;

public record StoriesRequest( String title, String description, String picture_front_pages,
                             String file_pdf, String status, LocalDate created_date, LocalDate published_date) {
}
