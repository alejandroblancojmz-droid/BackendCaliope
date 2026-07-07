package org.generation.caliope.controller;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.StoryGenresRequest;
import org.generation.caliope.model.StoryGenres;
import org.generation.caliope.service.StoryGenresService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story-genres")
@AllArgsConstructor
public class StoryGenresController {
    private final StoryGenresService storyGenresService;

    @PostMapping
    public StoryGenres addGenreToStory(@RequestBody StoryGenresRequest request) {
        return storyGenresService.assignGenreToStory(request);
    }

    @GetMapping("/story/{storyId}")
    public List<StoryGenres> getGenresByStory(@PathVariable Long storyId) {
        return storyGenresService.getGenresByStory(storyId);
    }

    @DeleteMapping("/{id}")
    public void deleteLink(@PathVariable Long id) {
        storyGenresService.removeGenreFromStory(id);
    }

}
