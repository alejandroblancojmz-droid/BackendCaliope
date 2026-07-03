package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoriesService {
    private final StoriesRepository storiesRepository;
    private final UsersRepository usersRepository;



    public Stories addStories(StoriesRequest storiesRequest){
        Users users = usersRepository.findById(storiesRequest.idUsers()).orElseThrow(
                () -> new IllegalArgumentException("No existe el usuario")
        );
        Stories newStorie = new Stories();

        if(storiesRequest.title() != null) newStorie.setTitle(storiesRequest.title());
        if(storiesRequest.description()!= null) newStorie.setDescription(storiesRequest.description());
        if(storiesRequest.picture_front_pages()!= null ) newStorie.setPicture_front_pages(storiesRequest.picture_front_pages());
        if(storiesRequest.file_pdf() != null) newStorie.setFile_pdf(storiesRequest.file_pdf());
        if(storiesRequest.status() != null) newStorie.setStatus(storiesRequest.status());
        if(storiesRequest.created_date() != null) newStorie.setCreated_date(storiesRequest.created_date());
        if(storiesRequest.published_date() != null) newStorie.setPublished_date(storiesRequest.published_date());

        newStorie.setUsers(users);
        users.getStories().add(newStorie);
        storiesRepository.save(newStorie);
        usersRepository.save(users);
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
