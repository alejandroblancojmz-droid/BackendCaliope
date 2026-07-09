package org.generation.caliope.controller;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.service.StoriesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/stories")// http://localhost:8080/api/stories
@AllArgsConstructor
public class StoriesController {

    private final StoriesService storiesService;

    @GetMapping
    public List<Map<String, Object>> getAllStories() {
        List<Stories> stories = storiesService.getAllStories();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Stories story : stories) {
            Map<String, Object> storyMap = new HashMap<>();
            storyMap.put("id", story.getId());
            storyMap.put("title", story.getTitle());
            storyMap.put("description", story.getDescription());
            storyMap.put("picture_front_pages", story.getPicture_front_pages());
            storyMap.put("file_pdf", story.getFile_pdf());
            storyMap.put("status", story.getStatus());
            storyMap.put("created_date", story.getCreated_date());
            storyMap.put("published_date", story.getPublished_date());
            storyMap.put("likes_count", story.getGetlikes() != null ? story.getGetlikes().size() : 0);

            Users user = story.getUsers();
            if (user != null) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("firstName", user.getFirstName());
                userMap.put("lastName", user.getLastName());
                userMap.put("username", user.getUser_name());
                userMap.put("user_name", user.getUser_name());
                userMap.put("picture_avatar", user.getPicture_avatar());
                storyMap.put("user", userMap);
            } else {
                storyMap.put("user", null);
            }

            result.add(storyMap);
        }
        return result;
    }

    @GetMapping(path = "{storiesId}")
    public Stories getSingleStories(@PathVariable("storiesId")Long id){
        return storiesService.getUserById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Stories addStoriesWithFiles(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("status") String status,
            @RequestParam(value = "genres", required = false) List<Long> genres,
            @RequestParam(value = "picture_front_pages", required = false) MultipartFile picture_front_pages,
            @RequestParam(value = "file_pdf", required = false) MultipartFile file_pdf
    ) throws IOException {

        // Crear el DTO con los datos
        StoriesRequest storiesRequest = new StoriesRequest(
                title,
                description,
                status,
                null, // created_date
                null, // published_date
                genres,
                picture_front_pages,
                file_pdf
        );

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
