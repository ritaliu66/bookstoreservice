package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreserviceApplication;
import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.entity.UserEntity;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BookstoreserviceApplication.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String USERNAME="2";

    private static final String PASSWORD="2";

    @Before
    public void init(){
        userService=new UserServiceImpl(userDao,passwordEncoder);
    }

    @Test
    void register() {
        UserEntity userEntity = new UserEntity(USERNAME, PASSWORD);

        Mockito.when(passwordEncoder.encode(userEntity.getPassword())).thenReturn(PASSWORD);
        UserEntity result = userService.register(userEntity);

        Assertions.assertNull(result);
    }
}