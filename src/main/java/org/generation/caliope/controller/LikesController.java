package org.generation.caliope.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.generation.caliope.dto.LikesRequest;
import org.generation.caliope.model.Likes;
import org.generation.caliope.service.LikesService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/stories/likes") // http://localhost:8080/api/stories/likes
@AllArgsConstructor

public class LikesController {

    private final LikesService likesService;

    @PostMapping("/toggle")
    public Map<String, Object> toggleLike(@RequestBody LikesRequest likesRequest, Authentication authentication) {
        String username = authentication.getName();
        return likesService.toggleLike(likesRequest, username);
    }

    @GetMapping("/story/{storiesId}")
    public Map<String, Object> getLikeInfo(@PathVariable Long storiesId, Authentication authentication) {
        String username = authentication.getName();
        return likesService.getLikeInfo(storiesId, username);
    }
}