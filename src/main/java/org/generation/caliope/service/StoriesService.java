package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Genres;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.StoryGenres;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.GenresRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.StoryGenresRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StoriesService {
    private final StoriesRepository storiesRepository;
    private final UsersRepository usersRepository;
    private final StoryGenresRepository storyGenresRepository;
    private final GenresRepository genresRepository;
    private final FileStorageService fileStorageService;

    // Directorio donde se guardarán los archivos
    private static final String UPLOAD_DIR = "uploads/";


    public Stories addStories(StoriesRequest storiesRequest)throws IOException {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));

        Stories newStorie = new Stories();

        if(storiesRequest.title() != null) newStorie.setTitle(storiesRequest.title());
        if(storiesRequest.description()!= null) newStorie.setDescription(storiesRequest.description());
        if(storiesRequest.status() != null) newStorie.setStatus(storiesRequest.status());
        newStorie.setCreated_date(LocalDateTime.now());
        newStorie.setPublished_date(LocalDateTime.now());


        // Procesar archivos
        MultipartFile pictureFile = storiesRequest.picture_front_pages();
        MultipartFile pdfFile = storiesRequest.file_pdf();

        // Crear directorio si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR);


        // Guardar portada
        if (pictureFile != null && !pictureFile.isEmpty()) {
            String pictureFileName = UUID.randomUUID().toString() + "_" + pictureFile.getOriginalFilename();
            Path picturePath = uploadPath.resolve(pictureFileName);
            Files.copy(pictureFile.getInputStream(), picturePath);
            newStorie.setPicture_front_pages(pictureFileName);
            System.out.println("Portada guardada: " + pictureFileName);
        }

        // Guardar PDF
        if (pdfFile != null && !pdfFile.isEmpty()) {
            String pdfFileName = UUID.randomUUID().toString() + "_" + pdfFile.getOriginalFilename();
            Path pdfPath = uploadPath.resolve(pdfFileName);
            Files.copy(pdfFile.getInputStream(), pdfPath);
            newStorie.setFile_pdf(pdfFileName);
            System.out.println("PDF guardado: " + pdfFileName);
        }


        newStorie.setUsers(users);
        users.getStories().add(newStorie);
        storiesRepository.save(newStorie);
        usersRepository.save(users);

        if (storiesRequest.genres() != null) {
            for (Long genreId : storiesRequest.genres()) {
                Genres genre = genresRepository.findById(genreId)
                        .orElseThrow(() -> new IllegalArgumentException("Género no encontrado: " + genreId));
                StoryGenres storyGenre = new StoryGenres();
                storyGenre.setStory(newStorie);
                storyGenre.setGenre(genre);
                storyGenresRepository.save(storyGenre);
            }
        }

        return newStorie;
    }

    public List<Stories> getAllStories(){
        return  storiesRepository.findAll();
    }

    public Stories getUserById(Long id) {
        return storiesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Historia no encontrada :p")
        );
    }

    public Stories deleteUserById(Long id){
        Stories obj = storiesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Historia no encontrada :p")
        );
        storiesRepository.delete(obj);
        return obj;
    }

    public Stories updateStoriesById(Long id, Stories updateStores){
        Stories savedStories = storiesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Historia no encontrada :p")
        );

        if(updateStores.getTitle() != null) savedStories.setTitle(updateStores.getTitle());
        if(updateStores.getDescription() != null) savedStories.setDescription(updateStores.getDescription());
        if(updateStores.getPicture_front_pages() != null) savedStories.setPicture_front_pages(updateStores.getPicture_front_pages());
        if(updateStores.getStatus() != null) savedStories.setStatus(updateStores.getStatus());
        if(updateStores.getCreated_date() != null) savedStories.setCreated_date(updateStores.getCreated_date());
        if(updateStores.getPublished_date() != null) savedStories.setPublished_date(updateStores.getPublished_date());

        return storiesRepository.save(savedStories);
    }
}
