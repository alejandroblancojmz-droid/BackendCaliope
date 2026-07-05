package org.generation.caliope.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Stories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=1,max=150)
    private String title;

    private String description;

    private String picture_front_pages;

    private String file_pdf;

    private String status;

    private LocalDate created_date;

    private LocalDate published_date;

    //Declacarion del hijo
    @ManyToOne
    @JoinColumn(name = "stories_id", nullable = false)
    @JsonIgnore
    private Users users;

    //Relación con la biblioteca (historias guardadas por usuarios)
    @OneToMany(mappedBy = "stories", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Library> libraryEntries;

    //Declaracion de Padre -> Likes
    @OneToMany(mappedBy = "stories", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> getlikes;


}
