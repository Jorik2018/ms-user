package com.nttdata.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.user.service.*;
import com.nttdata.user.model.*;
import com.nttdata.user.model.entity.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john@example.com");
        userRequest.setPassword("password");

        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        phone.setNumber("123456");
        phone.setCityCode("1");
        phone.setCountryCode("57");
        phones.add(phone);

        userRequest.setPhones(phones);

        User savedUser = new User();
        savedUser.setId("1");
        savedUser.setName("John Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setCreated(LocalDateTime.now());
        savedUser.setModified(LocalDateTime.now());
        savedUser.setLastLogin(LocalDateTime.now());
        savedUser.setToken("token");
        savedUser.setActive(true);

        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        webTestClient.post()
                .uri("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.isActive").isEqualTo(true); // Add more assertions as needed
    }

    @Test
public void testCreateUser_InvalidRequest() {
    UserRequest userRequest = new UserRequest();
    userRequest.setEmail("John Doe");
    webTestClient.post()
            .uri("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(userRequest)
            .exchange()
            .expectStatus().isBadRequest();
}

}
