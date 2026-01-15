package cat.itacademy.s04.t01.userapi.model;

import java.util.UUID;

public record User(UUID id, String name, String email) {
    public static User create(String name, String email) {
        return new User(UUID.randomUUID(), name, email);
    }
}
