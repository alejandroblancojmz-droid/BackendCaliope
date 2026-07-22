package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.LikesRequest;
import org.generation.caliope.model.Likes;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.LikesRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final StoriesRepository storiesRepository;
    private final UsersRepository usersRepository;

    // Da o quita el like (toggle) para el usuario autenticado
    public Map<String, Object> toggleLike(LikesRequest request, String emailAutenticado) {
        Users user = usersRepository.findByEmail(emailAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Stories story = storiesRepository.findById(request.idStories())
                .orElseThrow(() -> new IllegalArgumentException("Historia no encontrada"));

        Optional<Likes> existente = likesRepository.findByStories_IdAndUsers_Id(story.getId(), user.getId());

        boolean dioLike;
        if (existente.isPresent()) {
            likesRepository.delete(existente.get());
            dioLike = false;
        } else {
            Likes like = new Likes();
            like.setCreated_date(LocalDate.now());
            like.setStories(story);
            like.setUsers(user);
            likesRepository.save(like);
            dioLike = true;
        }

        long total = likesRepository.countByStories_Id(story.getId());

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("liked", dioLike);
        resultado.put("totalLikes", total);
        return resultado;
    }

    // Info inicial para pintar el botón al cargar la página
    public Map<String, Object> getLikeInfo(Long storiesId, String emailAutenticado) {
        Users user = usersRepository.findByEmail(emailAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        boolean liked = likesRepository.findByStories_IdAndUsers_Id(storiesId, user.getId()).isPresent();
        long total = likesRepository.countByStories_Id(storiesId);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("liked", liked);
        resultado.put("totalLikes", total);
        return resultado;
    }
}