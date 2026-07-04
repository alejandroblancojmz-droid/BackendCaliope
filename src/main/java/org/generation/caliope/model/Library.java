package org.generation.caliope.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "library",
       uniqueConstraints = @UniqueConstraint(columnNames = {"users_idusers", "stories_idstories"}))
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_idusers", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stories_idstories", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Stories stories;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt;

}
