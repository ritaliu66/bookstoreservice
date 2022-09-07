package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
;
import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.epam.bookstoreservice.mapper.TokenResponseDTOAssembler;
import com.epam.bookstoreservice.model.TokenModel;
import com.epam.bookstoreservice.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

/**
 * unit test for login controller
 */
@SpringBootTest(classes = BookstoreServiceApplication.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginServiceImpl loginService;

    @Autowired
    private TokenResponseDTOAssembler tokenResponseDTOAssembler;

    private final static String USERNAME = "2";

    private final static String PASSWORD = "2";

    private final static String PHONE_NUMBER = "111";

    private final static TokenResponseDTO  TOKEN = new TokenResponseDTO("Bearer ","token");

    @BeforeEach
    public void init() {
        loginController = new LoginController(loginService,tokenResponseDTOAssembler);
    }

    @Test
    void loginAndReturnTokenWhenUseCorrectUsernameAndPassword() {

        UserRequestDTO userRequestDto = new UserRequestDTO(USERNAME, PASSWORD, PHONE_NUMBER);

        Mockito.when(loginService.loginAndReturnToken(userRequestDto)).thenReturn(TOKEN);

        ResponseEntity<TokenModel> token = loginController.loginAndReturnToken(userRequestDto);

        Assertions.assertNotNull(Objects.requireNonNull(token.getBody()).getContent());
    }

}