package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.dto.CheckResponse;
import org.generation.caliope.dto.LibraryResponse;

import org.generation.caliope.model.Library;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.LibraryRepository;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final UsersRepository usersRepository;
    private final StoriesRepository storiesRepository;

    @Transactional
    public LibraryResponse saveStory(Long userId, Long storyId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Stories story = storiesRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("Historia no encontrada"));

        if (libraryRepository.existsByUsersIdAndStoriesId(userId, storyId)) {
            throw new IllegalArgumentException("La historia ya está guardada en tu biblioteca");
        }

        Library library = new Library();
        library.setUsers(user);
        library.setStories(story);
        library.setSavedAt(LocalDateTime.now());
        libraryRepository.save(library);

        return mapToResponse(library);
    }

    @Transactional(readOnly = true)
    public List<LibraryResponse> getUserLibrary(Long userId) {
        if (!usersRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        return libraryRepository.findByUsersId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void removeStory(Long userId, Long storyId) {
        if (!usersRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Library library = libraryRepository.findByUsersIdAndStoriesId(userId, storyId)
                .orElseThrow(() -> new IllegalArgumentException("La historia no está en tu biblioteca"));

        libraryRepository.delete(library);
    }

    @Transactional(readOnly = true)
    public List<Long> getStorySavers(Long storyId) {
        if (!storiesRepository.existsById(storyId)) {
            throw new IllegalArgumentException("Historia no encontrada");
        }

        return libraryRepository.findByStoriesId(storyId)
                .stream()
                .map(l -> l.getUsers().getId())
                .toList();
    }

    @Transactional(readOnly = true)
    public CheckResponse checkStory(Long userId, Long storyId) {
        if (!usersRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        boolean saved = libraryRepository.existsByUsersIdAndStoriesId(userId, storyId);
        return new CheckResponse(saved);
    }

    private LibraryResponse mapToResponse(Library library) {
        return new LibraryResponse(
                library.getId(),
                library.getUsers().getId(),
                library.getStories().getId(),
                library.getSavedAt()
        );
    }

}
