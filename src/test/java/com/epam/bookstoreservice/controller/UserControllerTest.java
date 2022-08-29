package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

/**
 * unit test for user controller
 */
@SpringBootTest(classes = BookstoreServiceApplication.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    private static final String USERNAME = "2";

    private static final String PASSWORD = "2";

    private final static String PHONE_NUMBER = "111";

    @BeforeEach
    public void init() {
        userController = new UserController(userService);
    }

    @Test
    void register() {
        UserRequestDTO userRequestDto = new UserRequestDTO(USERNAME, PASSWORD, PHONE_NUMBER);
        UserResponseDTO userResponseDTO = new UserResponseDTO(USERNAME, PHONE_NUMBER);

        Mockito.when(userService.registerAUser(any())).thenReturn( userResponseDTO);

        ResponseEntity<UserResponseDTO> result = userController.registerAUser(userRequestDto);
        Assertions.assertNotNull(result.getBody());
    }

}