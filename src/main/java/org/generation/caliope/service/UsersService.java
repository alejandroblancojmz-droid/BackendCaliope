package org.generation.caliope.service;

import lombok.AllArgsConstructor;
import org.generation.caliope.controller.StoriesController;
import org.generation.caliope.dto.LoginRequest;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final StoriesRepository storiesRepository;
    private final JwtService jwtService;




    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found!")
        );
    }

    public Users createUsers(Users users) {
        String encriptedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encriptedPassword);
        usersRepository.save(users);
        return usersRepository.save(users);
    }

    public Users deleteUserById(Long id) {
        Users tmp = usersRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found!")
        );
        usersRepository.delete(tmp);
        return tmp;
    }

    public Users updateUsersById(Long id, Users updatedUsers) {
        Users savedUsers = usersRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found!")
        );
        if (updatedUsers.getFirstName() != null) savedUsers.setFirstName(updatedUsers.getFirstName());
        if (updatedUsers.getLastName() != null) savedUsers.setLastName(updatedUsers.getLastName());
        if (updatedUsers.getUser_name() != null) savedUsers.setUser_name(updatedUsers.getUser_name());
        if (updatedUsers.getEmail() != null) savedUsers.setEmail(updatedUsers.getEmail());
        if (updatedUsers.getBirthday() != null) savedUsers.setBirthday(updatedUsers.getBirthday());
        if (updatedUsers.getBio() != null) savedUsers.setBio(updatedUsers.getBio());
        if (updatedUsers.getPicture_avatar() != null) savedUsers.setPicture_avatar(updatedUsers.getPicture_avatar());
        if (updatedUsers.getPassword() != null) savedUsers.setPassword(updatedUsers.getPassword());
        savedUsers.setCreated_at(LocalDateTime.now());

        return usersRepository.save(savedUsers);
    }

    public Users addStoriesUser(Long usersId, StoriesRequest storiesRequest) {
        Users savedUsers = usersRepository.findById(usersId).orElseThrow(
                () -> new IllegalArgumentException("Usuario no encontrado")
        );
        //2. Si existe el usuario crea una storie vacia
        Stories stories = new Stories();
        /*
          3. Verificamos la informmación que viene en la storie.request
          si viene la información se la asignamos a la storie
         */
        if (storiesRequest.title() != null) stories.setTitle(storiesRequest.title());
        if (storiesRequest.description() != null) stories.setDescription(storiesRequest.description());
        if (storiesRequest.picture_front_pages() != null)
            stories.setPicture_front_pages(storiesRequest.picture_front_pages());
        if (storiesRequest.file_pdf() != null) stories.setFile_pdf(storiesRequest.file_pdf());
        if (storiesRequest.status() != null) stories.setStatus(storiesRequest.status());
        if (storiesRequest.created_date() != null) stories.setCreated_date(LocalDateTime.now());
        if (storiesRequest.published_date() != null) stories.setPublished_date(LocalDateTime.now());
        //4.Asignando el artista al que pertenece la storie
        stories.setUsers(savedUsers);
        //5.Guardando la storie
        storiesRepository.save(stories);
        //6.Actualizando la lista de stories del usuario
        savedUsers.getStories().add(stories);
        //7.Actualizando el usuario con sus stories en la base de datos
        return usersRepository.save(savedUsers);
    }

    public String loginUser(LoginRequest loginRequest){

        Users savedUser = usersRepository.findByEmail(loginRequest.email())
                .orElseThrow(() ->
                        new IllegalArgumentException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(
                loginRequest.password(),
                savedUser.getPassword())) {

            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return jwtService.generateToken(savedUser);
    }

    public Users findByEmail(String email){

        return usersRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));
    }


    public Users getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}
