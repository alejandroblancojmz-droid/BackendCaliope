package org.generation.caliope.controller;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.ReviewsRequest;
import org.generation.caliope.model.Reviews;
import org.generation.caliope.service.ReviewsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/reviews")
@AllArgsConstructor
public class ReviewsController {
    private final ReviewsService reviewsService;

    @GetMapping
    public List<Reviews> getAllReviews() {
        return reviewsService.getAllReviews();
    }

    @GetMapping(path = "{reviewsId}")
    public Reviews getReviewById(@PathVariable("reviewsId") Long id) {
        return reviewsService.getReviewById(id);
    }

    // Reseñas de una historia en específico
    @GetMapping(path = "/story/{storiesId}")
    public List<Reviews> getReviewsByStory(@PathVariable Long storiesId) {
        return reviewsService.getReviewsByStory(storiesId);
    }

    @PostMapping
    public Reviews addReview(@RequestBody ReviewsRequest reviewsRequest, Authentication authentication) {
        String email = authentication.getName();
        return reviewsService.addReview(reviewsRequest, email);
    }

    @DeleteMapping(path = "{reviewsId}")
    public Reviews deleteReviewById(@PathVariable("reviewsId") Long id) {
        return reviewsService.deleteReview(id);
    }

    @PutMapping(path = "{reviewsId}")
    public Reviews updateReview(@PathVariable("reviewsId") Long id, @RequestBody ReviewsRequest updatedReview) {
        return reviewsService.updateReviewById(id, updatedReview);
    }
}