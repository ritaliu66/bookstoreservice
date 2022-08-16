package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.dto.response.UserResponseDto;
import com.epam.bookstoreservice.mapper.BookDtoToBookEntityMapper;
import com.epam.bookstoreservice.mapper.UserDtoToUserEntityMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(classes = BookstoreServiceApplication.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String USERNAME = "2";

    private static final String PASSWORD = "2";

    private final static Integer PHONE_NUMBER = 111;

    private final UserDtoToUserEntityMapper userDtoToUserEntityMapper = Mappers.getMapper(
            UserDtoToUserEntityMapper.class);

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userDao,passwordEncoder, userDtoToUserEntityMapper);
    }
    @Test
    void register() {
        UserRequestDto userRequestDto = new UserRequestDto(USERNAME, PASSWORD, PHONE_NUMBER);

        Mockito.when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn(PASSWORD);

        UserResponseDto responseDto = userService.register(userRequestDto);

        Assertions.assertNotNull(responseDto);
    }
}