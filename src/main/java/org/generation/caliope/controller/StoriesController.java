package org.generation.caliope.controller;


import lombok.AllArgsConstructor;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.service.StoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/stories")// http://localhost:8080/api/stories
@AllArgsConstructor
public class StoriesController {

    private final StoriesService storiesService;

    @GetMapping
    public List<Stories> getAllStories(){
        return storiesService.getAllStories();
    }

    @GetMapping(path = "{storiesId}")
    public Stories getSingleStories(@PathVariable("storiesId")Long id){
        return storiesService.getUserById(id);
    }

    @PostMapping // http://localhost:8080/api/stories con el metodo POST
    public Stories addStories(@RequestBody StoriesRequest storiesRequest){
        return storiesService.addStories(storiesRequest);
    }

    @DeleteMapping(path = "{storiesId}")
    public Stories deleteStoriesById(@PathVariable("storiesId") Long id){
        return storiesService.deleteUserById(id);
    }

    @PutMapping(path = "{storiesId}")
    public Stories updateStoriesById(@PathVariable("storiesId") Long id, @RequestBody Stories updateStories){
        return storiesService.updateStoriesById(id, updateStories);
    }


}
