package org.generation.caliope.service;

import lombok.AllArgsConstructor;

import org.generation.caliope.dto.LoginRequest;

import org.generation.caliope.model.Users;
import org.generation.caliope.repository.StoriesRepository;
import org.generation.caliope.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
public class UsersService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
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
        System.out.println(authentication);
        System.out.println(authentication.getName());


        String email = authentication.getName();

        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public Users updateAvatar(Long id, MultipartFile avatar) throws IOException {
        Users savedUsers = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if (avatar != null && !avatar.isEmpty()) {
            Path uploadPath = Paths.get("/home/ubuntu/src/uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Guardar archivo
            String fileName = UUID.randomUUID().toString() + "_" + avatar.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(avatar.getInputStream(), filePath);

            savedUsers.setPicture_avatar(fileName);
        }

        return usersRepository.save(savedUsers);
    }
}
