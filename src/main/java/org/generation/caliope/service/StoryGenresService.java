package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.StoryGenresRequest;
import org.generation.caliope.model.Genres;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.StoryGenres;
import org.generation.caliope.repository.GenresRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.StoryGenresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoryGenresService {
    private final StoryGenresRepository storyGenresRepository;
    private final StoriesRepository storiesRepository;
    private final GenresRepository genresRepository;

    public StoryGenres assignGenereToStory(StoryGenresRequest request){
        Stories story = storiesRepository.findById(request.storyId()).orElseThrow(
                () -> new IllegalArgumentException("Historia no encontrada")
        );
        Genres genre = genresRepository.findById(request.genreId()).orElseThrow(
                () -> new IllegalArgumentException("Género no encontrado")
        );

        StoryGenres storyGenre = new StoryGenres();
        storyGenre.setStory(story);
        storyGenre.setGenre(genre);

        return storyGenresRepository.save(storyGenre);
    }

    public List<StoryGenres> getGenresByStory(Long storyId) {
        return storyGenresRepository.findByStoryId(storyId);
    }

    public void removeGenreFromStory(Long id) {
        storyGenresRepository.deleteById(id);
    }


    public StoryGenres assignGenreToStory(StoryGenresRequest request) {
        return null;
    }
}
