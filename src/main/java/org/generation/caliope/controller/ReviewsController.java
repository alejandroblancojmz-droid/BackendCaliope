package org.generation.caliope.controller;
import org.generation.caliope.dto.ReviewsRequest;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Reviews;
import org.generation.caliope.dto.UsersRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import lombok.AllArgsConstructor;
import org.generation.caliope.service.ReviewsService;
import org.generation.caliope.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/reviews") //// http://localhost:8080/api/Reviews
@AllArgsConstructor
public class ReviewsController {
    private  final ReviewsService reviewsService;

    @GetMapping
    public List<Reviews> getAllReviews(){
        return reviewsService.getAllReviews();
    }

    @GetMapping(path = "{reviewsId}")
    public Reviews getReviewById(@PathVariable("reviewsId") Long id){
        return reviewsService.getReviewById(id);
    }

    @PostMapping // http://localhost:8080/api/stories con el metodo POST
    public Reviews addReview(@RequestBody ReviewsRequest reviewsRequest){
        return reviewsService.addReview(reviewsRequest);
    }

    @DeleteMapping(path = "{reviewsId}")
    public Reviews deleteStoriesById(@PathVariable("reviewsId") Long id){
        return reviewsService.deleteReview(id);
    }

    @PutMapping(path = "{reviewsId}")
    public Reviews updateReview(@PathVariable("reviewsId") Long id, @RequestBody Reviews updatedReview){
        return reviewsService.updateReviesById(id,updatedReview);
    }

}
