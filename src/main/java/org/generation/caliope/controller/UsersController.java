package org.generation.caliope.controller;

import org.generation.caliope.dto.LoginRequest;
import org.generation.caliope.dto.LoginResponse;
import org.generation.caliope.dto.StoriesRequest;
import org.generation.caliope.model.Stories;
import org.generation.caliope.model.Users;
import org.generation.caliope.repository.UsersRepository;
import org.generation.caliope.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/users") // http://localhost:8080/api/users
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping // http://localhost:8080/api/users
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping(path = "{userId}")
    // http://localhost:8080/api/users/userId
    public Users getSingleUser(@PathVariable("userId") Long id) {
        return usersService.getUserById(id);
    }

    @PostMapping // http://localhost:8080/api/users con el metodo POST
    public Users addUser(@RequestBody Users user) {
        return usersService.createUsers(user);
    }

    @DeleteMapping(path = "{userId}")
    public Users deleteUserById(@PathVariable("userId") Long id) {
        return usersService.deleteUserById(id);
    }

    @PutMapping(path = "{userId}")
    public Users updateUserById(@PathVariable("userId") Long id, @RequestBody Users updatedUser) {
        return usersService.updateUsersById(id, updatedUser);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Users user = usersService.findByEmail(loginRequest.email());
        String token = usersService.loginUser(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token, user.getId()));
    }

    //
    @GetMapping("/me")
    public Users getMyProfile() {
        return usersService.getAuthenticatedUser();
    }


}
