package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getUsers_withBlankName_returnsAllUsers() throws Exception{
        mockMvc.perform(get("/users").param("name", " "))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        CreateUserRequest user1 = new CreateUserRequest("JRC", "jrc@gmail.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user1)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users")
                        .param("name"," "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("JRC"))
                .andExpect(jsonPath("$[0].email").value("jrc@gmail.com"));
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Captain Kirk", "capkirk@gmail.com");
        String json = toJson(request);

        mockMvc.perform(post(
                "/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Captain Kirk"))
                .andExpect(jsonPath("$.email").value("capkirk@gmail.com"));
    }

    @Test
    void createUser_returnsConflictWhenEmailAlreadyExists() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Alan", "asd@gmail.com");
        String existingJson = toJson(request);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(existingJson))
                .andExpect(status().isCreated());

        CreateUserRequest newUser = new CreateUserRequest("Alan2", "asd@gmail.com");
        String newJson = toJson(newUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("EMAIL_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").value("User with email asd@gmail.com already exists."));
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception{
        String requestBody = toJson(new CreateUserRequest("Lieutenant","lieu@gmail.com"));

        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);
        UUID id = createdUser.id();

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Lieutenant"))
                .andExpect(jsonPath("$.email").value("lieu@gmail.com"));
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/users/" + randomId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {
        CreateUserRequest user1 = new CreateUserRequest("Jordi","ccr@gmail.com");
        CreateUserRequest user2 = new CreateUserRequest("JRC", "jrc@gmail.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user1)))
                        .andExpect(status().isCreated());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user2)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users")
                .param("name","jo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Jordi"))
                .andExpect(jsonPath("$[0].email").value("ccr@gmail.com"));
    }

    //HELPER
    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }
}
