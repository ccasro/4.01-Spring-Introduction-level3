package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.stream().filter(u -> u.id().equals(id))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name){
        String lower = name.toLowerCase();
        return users.stream().filter(u -> u.name().toLowerCase().contains(lower)).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(u->u.email().equalsIgnoreCase(email));
    }
}
