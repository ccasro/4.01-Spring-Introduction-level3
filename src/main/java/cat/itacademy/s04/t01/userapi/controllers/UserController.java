package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private static List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers() {
        return List.copyOf(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request){
        User newUser = User.create(request.name(), request.email());
        users.add(newUser);

        return ResponseEntity.created(URI.create("/users/" + newUser.id())).body(newUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return users.stream().filter(u -> u.id().equals(id)).findFirst()
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @GetMapping(params = "name")
    public List<User> getUsersByName(@RequestParam String name) {
        String lowerName = name.toLowerCase();
        return users.stream()
                .filter(u -> u.name().toLowerCase().contains(lowerName))
                .toList();
    }

    static void clearUsers(){
        users.clear();
    }


}
