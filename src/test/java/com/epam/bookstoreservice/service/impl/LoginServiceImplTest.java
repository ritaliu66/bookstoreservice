package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreserviceApplication;
import com.epam.bookstoreservice.config.jwt.JwtTokenUtil;
import com.epam.bookstoreservice.config.security.UserDetail;
import com.epam.bookstoreservice.entity.UserEntity;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest(classes = BookstoreserviceApplication.class)
class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private final static String USERNAME = "2";

    private final static String CORRECT_PASSWORD = "2";


    private final static String INCORRECT_PASSWORD = "4";

    @BeforeEach
    public void init(){
        loginService = new LoginServiceImpl(userDetailsService,passwordEncoder,jwtTokenUtil);
    }

    @Test
    void loginWhenUseCorrectUsernameAndPassword() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(USERNAME);
        userDetail.setPassword(CORRECT_PASSWORD);

        Mockito.when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetail);

        UserEntity userEntity = new UserEntity(USERNAME, CORRECT_PASSWORD);

        Mockito.when(passwordEncoder.matches(userEntity.getPassword(),userDetail.getPassword())).thenReturn(true);

        String result = loginService.login(userEntity);

        Assertions.assertNotNull(result);
    }

    @Test
    void loginWhenUseWrongUsernameOrPassword(){
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(USERNAME);
        userDetail.setPassword(CORRECT_PASSWORD);

        Mockito.when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetail);

        UserEntity userEntity2 = new UserEntity(USERNAME, INCORRECT_PASSWORD);
        String result2 = loginService.login(userEntity2);

        Assertions.assertEquals(result2,"failedWrong user name or password");
    }

    @Test
    void loginWhenUseEmptyUsernameOrPassword(){

        Mockito.when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(null);

        UserEntity userEntity2 = new UserEntity(USERNAME, INCORRECT_PASSWORD);
        String result2 = loginService.login(userEntity2);

        Assertions.assertEquals(result2,"failedWrong user name or password");
    }

}