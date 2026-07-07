package org.generation.caliope.controller;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.LikesRequest;
import org.generation.caliope.model.Likes;
import org.generation.caliope.service.LikesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/stories/likes") // http://localhost:8080/api/stories/likes
@AllArgsConstructor

public class LikesController {

    private final LikesService likesService;

    @GetMapping
    public List<Likes> getAllLikes(){
        return likesService.getAllLikes();
    }

    @GetMapping(path = "{likesId}")
    public Likes getSingleLikes(@PathVariable("likesId")long id){
        return likesService.getLikeById(id);
    }


    @PostMapping //http://localhost:8080/api/stories/likes
    public Likes addLikes(@RequestBody LikesRequest likesRequest){
        return likesService.addLike(likesRequest);
    }

    @DeleteMapping(path = "{likesId}")
    public Likes deleteLikesById(@PathVariable("likesId") Long id){
        return likesService.deleteLikeById(id);
    }

    }