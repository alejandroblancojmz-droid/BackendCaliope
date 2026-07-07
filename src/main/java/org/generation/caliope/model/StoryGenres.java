package org.generation.caliope.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StoryGenres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_story_genre")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "stories_id", nullable = false)
    @JsonIgnore
    private Stories story;

    @ManyToOne
    @JoinColumn(name = "genres_id", nullable = false)
    private Genres genre;

}
