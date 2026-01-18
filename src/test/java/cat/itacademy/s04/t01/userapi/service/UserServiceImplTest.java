package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists(){

        CreateUserRequest request = new CreateUserRequest("Alan", "alan@mail.com");

        when(userRepository.existsByEmail("alan@mail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(request);
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_shouldSaveUserWhenEmailDoesNotExist() {

        CreateUserRequest request = new CreateUserRequest("Angel", "angel@mail.com");

        when(userRepository.existsByEmail("angel@mail.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0) );

        User result = userService.createUser(request);

        assertNotNull(result.id());
        assertEquals("Angel", result.name());
        assertEquals("angel@mail.com", result.email());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
