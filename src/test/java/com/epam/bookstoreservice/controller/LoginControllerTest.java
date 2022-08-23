package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
;
import com.epam.bookstoreservice.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

/**
 * unit test for login controller
 */
@SpringBootTest(classes = BookstoreServiceApplication.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginServiceImpl loginService;

    private final static String USERNAME = "2";

    private final static String PASSWORD = "2";

    private final static String PHONE_NUMBER = "111";

    private final static String TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEsImNyZWF0ZWQiOjE2NjA2Mjc0MTI3MDQsImV4cCI6MTY2MTIzMjIxMn0.fJXfBpXSLnFMOjewMnPjZNMmpNLeNJ97w7kj3EQWhp45HrWZt-H8-emayKjYkZ3AtPUUYZ1PSJlRML0mGBsI3w";

    @BeforeEach
    public void init() {
        loginController = new LoginController(loginService);
    }

    @Test
    void loginAndReturnTokenWhenUseCorrectUsernameAndPassword() {

        UserRequestDTO userRequestDto = new UserRequestDTO(USERNAME, PASSWORD, PHONE_NUMBER);

        Mockito.when(loginService.loginAndReturnToken(userRequestDto)).thenReturn(TOKEN);

        ResponseEntity<Result<String>> token = loginController.loginAndReturnToken(userRequestDto);

        Assertions.assertNotNull(token.getBody().getData());
    }

}