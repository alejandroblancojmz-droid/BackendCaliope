package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.model.Reviews;
import org.generation.caliope.dto.ReviewsRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.repository.ReviewsRepository;
import org.generation.caliope.model.Users;
import org.generation.caliope.dto.UsersRequest;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;

    public  Reviews addReview(ReviewsRequest reviewsRequest){
        Users users = usersRepository.findById(reviewsRequest.id()).orElseThrow(
                () -> new IllegalArgumentException("No existe el usuario")
        );
        Reviews newReview = new Reviews();
        if(reviewsRequest.rate()     != null) newReview.setRate(reviewsRequest.rate());
        if(reviewsRequest.review()   != null) newReview.setReview(reviewsRequest.review());
        newReview.setCreation(LocalDateTime.now());


        newReview.setUsers(users);
        users.getReviews().add(newReview);
        reviewsRepository.save(newReview);
        usersRepository.save(users);
        return  newReview;
    }

    public Reviews updateReviesById(Long id, Reviews updatedReview){
        Reviews savedReview = reviewsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Reseña no encontrada :p")
        );
        if(savedReview.getRate()     != null) savedReview.setRate(savedReview.getRate());
        if(savedReview.getReview()   != null) savedReview.setReview(savedReview.getReview());
        savedReview.setCreation(LocalDateTime.now());

        return reviewsRepository.save(savedReview);
    }

    public Reviews deleteReview(Long id){
        Reviews selectReview = reviewsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Reseña no encontrada :p")
        );
        reviewsRepository.delete(selectReview);
        return selectReview;
    }

    public List<Reviews> getAllReviews(){
        return reviewsRepository.findAll();
    }

    public Reviews getReviewById(Long id){
        return reviewsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Reseña no encontrada :p")
        );
    }




}
