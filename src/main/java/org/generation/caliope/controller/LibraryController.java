package org.generation.caliope.controller;

import org.generation.caliope.dto.CheckResponse;
import org.generation.caliope.dto.LibraryResponse;
import org.generation.caliope.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/{storyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryResponse saveStory(
            @RequestParam Long userId,
            @PathVariable Long storyId) {
        return libraryService.saveStory(userId, storyId);
    }

    @GetMapping
    public List<LibraryResponse> getUserLibrary(@RequestParam Long userId) {
        return libraryService.getUserLibrary(userId);
    }

    @DeleteMapping("/{storyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeStory(
            @RequestParam Long userId,
            @PathVariable Long storyId) {
        libraryService.removeStory(userId, storyId);
    }

    @GetMapping("/check/{storyId}")
    public CheckResponse checkStory(
            @RequestParam Long userId,
            @PathVariable Long storyId) {
        return libraryService.checkStory(userId, storyId);
    }

    @GetMapping("/story/{storyId}")
    public List<Long> getStorySavers(@PathVariable Long storyId) {
        return libraryService.getStorySavers(storyId);
    }

}
