package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserRequest request);

    List<User> getAllUsers();

    User getUserById(UUID id);

    List<User> searchByName(String name);
}
