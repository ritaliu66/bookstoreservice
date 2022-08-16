package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.config.jwt.JwtTokenUtil;
import com.epam.bookstoreservice.config.security.UserDetail;
import com.epam.bookstoreservice.config.security.UserDetailsServiceImpl;
import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.model.LoginUserDetails;
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

    private final static Integer PHONE_NUMBER = 111;

    @BeforeEach
    public void init(){
        loginService = new LoginServiceImpl(userDetailsService,passwordEncoder,jwtTokenUtil);
    }

    @Test
    void loginWhenUseCorrectUsernameAndPassword() {
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.setUsername(USERNAME);
        loginUserDetails.setPassword(CORRECT_PASSWORD);

        Mockito.when(userDetailsService.loadUserByPhoneNumber(PHONE_NUMBER)).thenReturn(loginUserDetails);

        UserRequestDto userRequestDto = new UserRequestDto(USERNAME,CORRECT_PASSWORD,PHONE_NUMBER);

        Mockito.when(passwordEncoder.matches(userRequestDto.getPassword(),loginUserDetails.getPassword())).thenReturn(true);

        String result = loginService.login(userRequestDto);

        Assertions.assertNotNull(result);
    }

    @Test
    void loginWhenUseWrongUsernameOrPassword(){
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.setUsername(USERNAME);
        loginUserDetails.setPassword(CORRECT_PASSWORD);

        Mockito.when(userDetailsService.loadUserByPhoneNumber(PHONE_NUMBER)).thenReturn(loginUserDetails);

        UserRequestDto userRequestDto = new UserRequestDto(USERNAME, INCORRECT_PASSWORD,PHONE_NUMBER);
        String result2 = loginService.login(userRequestDto);

        Assertions.assertEquals(result2,"failedWrong user name or password");
    }

    @Test
    void loginWhenUseEmptyUsernameOrPassword(){

        Mockito.when(userDetailsService.loadUserByPhoneNumber(PHONE_NUMBER)).thenReturn(null);

        UserRequestDto userEntity2 = new UserRequestDto(USERNAME, INCORRECT_PASSWORD,PHONE_NUMBER);
        String result2 = loginService.login(userEntity2);

        Assertions.assertEquals(result2,"failedWrong user name or password");
    }

}