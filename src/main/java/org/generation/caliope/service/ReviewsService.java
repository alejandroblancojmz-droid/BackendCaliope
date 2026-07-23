package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.ReviewsRequest;
import org.generation.caliope.model.Reviews;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.ReviewsRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;
    private final StoriesRepository storiesRepository;

    public Reviews addReview(ReviewsRequest reviewsRequest, String emailAutenticado) {
        Users users = usersRepository.findByEmail(emailAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario"));

        Stories story = storiesRepository.findById(reviewsRequest.idStories())
                .orElseThrow(() -> new IllegalArgumentException("Historia no encontrada"));

        boolean yaExiste = reviewsRepository.existsByStories_IdAndUsers_Id(story.getId(), users.getId());
        if (yaExiste) {
            throw new IllegalArgumentException("Ya dejaste una reseña para esta historia");
        }

        Reviews newReview = new Reviews();
        newReview.setRate(reviewsRequest.rate());
        newReview.setReview(reviewsRequest.review());
        newReview.setCreation(LocalDateTime.now());
        newReview.setUsers(users);
        newReview.setStories(story);

        return reviewsRepository.save(newReview);
    }

    public Reviews updateReviewById(Long id, ReviewsRequest updatedReview) {
        Reviews savedReview = reviewsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));

        if (updatedReview.rate() != null) savedReview.setRate(updatedReview.rate());
        if (updatedReview.review() != null) savedReview.setReview(updatedReview.review());

        return reviewsRepository.save(savedReview);
    }

    public Reviews deleteReview(Long id) {
        Reviews selectReview = reviewsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));
        reviewsRepository.delete(selectReview);
        return selectReview;
    }

    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    public Reviews getReviewById(Long id) {
        return reviewsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));
    }

    // Reseñas de una historia específica (lo que necesitas en la página de lectura)
    public List<Reviews> getReviewsByStory(Long storiesId) {
        return reviewsRepository.findByStories_Id(storiesId);
    }
}