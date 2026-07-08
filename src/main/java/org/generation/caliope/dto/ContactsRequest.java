package org.generation.caliope.dto;

import java.time.LocalDateTime;

public record ContactsRequest (String name,String subject,
                               String message,String email)  {
}