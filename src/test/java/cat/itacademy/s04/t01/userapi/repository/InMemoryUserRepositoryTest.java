package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    void setup() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void save_shouldStoreUser() {
        User user = User.create("Alan", "ala@gmail.com");
        repository.save(user);

        assertTrue(repository.findById(user.id()).isPresent());
    }

    @Test
    void findById_shouldReturnEmptyIfNotFound() {
        UUID randomId = UUID.randomUUID();
        assertTrue(repository.findById(randomId).isEmpty());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User u1 = User.create("ad","ad@gmail.com");
        User u2 = User.create("carol","carol@gmail.com");

        repository.save(u1);
        repository.save(u2);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void existsByEmail_shouldReturnTrueIfExists() {
        User user = User.create("As","As@gmail.com");
        repository.save(user);

        assertTrue(repository.existsByEmail("as@gmail.com"));
    }


}
