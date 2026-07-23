package org.generation.caliope.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"stories_id", "users_id"})
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate created_date;

    @ManyToOne
    @JoinColumn(name = "stories_id")
    @JsonIgnore
    private Stories stories;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "users_id")
    private Users users;

}
