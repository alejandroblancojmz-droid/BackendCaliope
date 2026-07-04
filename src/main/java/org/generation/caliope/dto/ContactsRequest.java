package org.generation.caliope.dto;

import java.time.LocalDateTime;

public record ContactsRequest (Long id ,String name,String subject,
                               String message,String email,LocalDateTime send)  {
}