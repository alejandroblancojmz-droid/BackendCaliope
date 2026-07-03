package org.generation.caliope.service;

import org.generation.caliope.dto.GenresRequest;
import org.generation.caliope.model.Genres;
import org.generation.caliope.repository.GenresRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresService {

    private final GenresRepository genresRepository;
    private final StoriesRepository storiesRepository;

    @Autowired

    public GenresService(GenresRepository genresRepository, StoriesRepository storiesRepository) {
        this.genresRepository = genresRepository;
        this.storiesRepository = storiesRepository;
    }

    public List<Genres> getAllGenres() {
        return genresRepository.findAll();
    }

    public Genres getGenreById(Long id) {
        return genresRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Genre not found!")
        );
    }

    public Genres createGenres(GenresRequest genresRequest) {
        Genres genres = new Genres();
        if (genresRequest.genre() != null) genres.setGenre(genresRequest.genre());
        return genresRepository.save(genres);
    }

    public Genres deleteGenreById(Long id) {
        Genres tmp = genresRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Genre not found!")
        );
        genresRepository.delete(tmp);
        return tmp;
    }

    public Genres updateGenresById(Long id, Genres updatedGenres) {
        Genres savedGenres = genresRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Genre not found!")
        );
        if (updatedGenres.getGenre() != null) savedGenres.setGenre(updatedGenres.getGenre());

        return genresRepository.save(savedGenres);
    }
}
