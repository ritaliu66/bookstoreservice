package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.exception.WrongPhoneNumberOrPassword;
import com.epam.bookstoreservice.security.jwt.JwtTokenUtil;
import com.epam.bookstoreservice.security.userdetailsservice.UserDetailsServiceImpl;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest(classes = BookstoreServiceApplication.class)
class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private final static String USERNAME = "2";

    private final static String CORRECT_PASSWORD = "2";


    private final static String INCORRECT_PASSWORD = "4";

    private final static String PHONE_NUMBER = "111";

    @BeforeEach
    public void init() {
        loginService = new LoginServiceImpl(userDetailsService, passwordEncoder, jwtTokenUtil);
    }

    @Test
    void loginWhenUseCorrectUsernameAndPassword() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(CORRECT_PASSWORD);

        Mockito.when(userDetailsService.loadUserByUsername(PHONE_NUMBER)).thenReturn(userEntity);

        UserRequestDTO userRequestDto = new UserRequestDTO(USERNAME, CORRECT_PASSWORD, PHONE_NUMBER);

        Mockito.when(passwordEncoder.matches(userRequestDto.getPassword(), userEntity.getPassword())).thenReturn(true);

        Result<String> token = loginService.loginAndReturnToken(userRequestDto);

        Assertions.assertNotNull(token.getData());
    }

    @Test
    void loginWhenUseWrongUsernameOrPassword() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(USERNAME);
            userEntity.setPassword(CORRECT_PASSWORD);

            Mockito.when(userDetailsService.loadUserByUsername(PHONE_NUMBER)).thenReturn(userEntity);
            UserRequestDTO userRequestDto = new UserRequestDTO(USERNAME, INCORRECT_PASSWORD, PHONE_NUMBER);
            loginService.loginAndReturnToken(userRequestDto);
        } catch (WrongPhoneNumberOrPassword ex) {
            Assert.assertEquals("login failed, wrong user name or password", ex.getErrorMsg());
        }

    }

    @Test
    void loginWhenUseEmptyUsernameOrPassword() {
        Mockito.when(userDetailsService.loadUserByUsername(PHONE_NUMBER)).thenReturn(null);
        UserRequestDTO userEntity = new UserRequestDTO(USERNAME, INCORRECT_PASSWORD, PHONE_NUMBER);
        try {
            loginService.loginAndReturnToken(userEntity);
            Assert.fail("Expected a WrongPhoneNumberOrPassword to be thrown");
        } catch (WrongPhoneNumberOrPassword ex) {
            Assert.assertEquals("login failed, wrong user name or password", ex.getErrorMsg());
        }


    }

}