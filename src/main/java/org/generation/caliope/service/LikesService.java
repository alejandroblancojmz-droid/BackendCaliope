package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.LikesRequest;
import org.generation.caliope.model.Likes;
import org.generation.caliope.model.Stories;
import org.generation.caliope.repository.LikesRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final StoriesRepository storiesRepository;

    // Crear un Like
    public Likes addLike(LikesRequest likesRequest) {

        Stories story = storiesRepository.findById(likesRequest.idStories())
                .orElseThrow(() -> new IllegalArgumentException("Historia no encontrada"));

        Likes like = new Likes();

        // La fecha se genera automáticamente
        like.setCreated_date(LocalDate.now());

        // Relación con la historia
        like.setStories(story);

        return likesRepository.save(like);
    }

    // Obtener todos los Likes
    public List<Likes> getAllLikes() {
        return likesRepository.findAll();
    }

    // Obtener un Like por ID
    public Likes getLikeById(Long id) {
        return likesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Like no encontrado"));
    }

    // Eliminar un Like
    public Likes deleteLikeById(Long id) {

        Likes like = likesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Like no encontrado"));

        likesRepository.delete(like);

        return like;
    }

    // Actualizar un Like
    public Likes updateLikeById(Long id, Likes updateLike) {

        Likes savedLike = likesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Like no encontrado"));

        if (updateLike.getCreated_date() != null) {
            savedLike.setCreated_date(updateLike.getCreated_date());
        }

        return likesRepository.save(savedLike);
    }

}
