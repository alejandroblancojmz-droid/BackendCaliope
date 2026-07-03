
package org.generation.caliope.controller;

import org.generation.caliope.dto.GenresRequest;
import org.generation.caliope.model.Genres;
import org.generation.caliope.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/genres") // http://localhost:8080/api/genres
public class GenresController {
    private final GenresService genresService;

    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping // http://localhost:8080/api/genres
    public List<Genres> getAllGenres() {
        return genresService.getAllGenres();
    }

    @GetMapping(path = "{genresId}")
    // http://localhost:8080/api/genres/genresId
    public Genres getSingleGenre(@PathVariable("genresId") Long id) {
        return genresService.getGenreById(id);
    }

    @PostMapping // http://localhost:8080/api/genres con el metodo POST
    public Genres addGenre(@RequestBody GenresRequest genresRequest) {
        return genresService.createGenres(genresRequest);
    }

    @DeleteMapping(path = "{genresId}")
    public Genres deleteGenreById(@PathVariable("genresId") Long id) {
        return genresService.deleteGenreById(id);
    }

    @PutMapping(path = "{genresId}")
    public Genres updateGenreById(@PathVariable("genresId") Long id, @RequestBody Genres updatedGenres) {
        return genresService.updateGenresById(id, updatedGenres);
    }
}