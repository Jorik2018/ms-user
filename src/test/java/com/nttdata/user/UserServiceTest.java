package com.nttdata.user;

import com.nttdata.user.exception.UserException;
import com.nttdata.user.model.entity.Phone;
import com.nttdata.user.model.entity.User;
import com.nttdata.user.repository.PhoneRepository;
import com.nttdata.user.repository.UserRepository;
import com.nttdata.user.service.UserService;
import com.nttdata.user.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PhoneRepository phoneRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        Mockito.when(jwtUtil.generateToken(Mockito.any(User.class))).thenReturn("sampleToken");
    }

    @Test
    public void testSaveUser_WithValidUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Password123");
        user.setPhones(new ArrayList<>());

        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);
        // Perform assertions on savedUser as needed
    }

    @Test
    public void testSaveUser_WithInvalidEmailFormat() {
        User user = new User();
        user.setEmail("invalidemail");
        user.setPassword("Password123");
        user.setPhones(new ArrayList<>());

        assertThrows(UserException.class, () -> userService.saveUser(user));
    }

    // Add more test cases for different scenarios...
}
