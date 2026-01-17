package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return service.getAllUsers();
        }
        return service.searchByName(name);
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request){
        User newUser = service.createUser(request);

        return ResponseEntity.created(URI.create("/users/" + newUser.id())).body(newUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return service.getUserById(id);
    }
}
