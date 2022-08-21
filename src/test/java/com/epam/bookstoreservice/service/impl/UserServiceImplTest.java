package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.mapper.UserDtoToUserEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;

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

    private final static String PHONE_NUMBER = "111";

    private final static Integer ID = 1;

    private final UserDtoToUserEntityMapper userDtoToUserEntityMapper = Mappers.getMapper(
            UserDtoToUserEntityMapper.class);

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userDao, passwordEncoder, userDtoToUserEntityMapper);
    }

    @Test
    void register() {
        UserRequestDTO userRequestDto = new UserRequestDTO(USERNAME, PASSWORD, PHONE_NUMBER);
        UserEntity userEntity = new UserEntity(ID, USERNAME, PASSWORD, PHONE_NUMBER);

        Mockito.when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn(PASSWORD);

        Mockito.when(userDao.save(any())).thenReturn(userEntity);

        Result<UserResponseDTO> registerResult = userService.register(userRequestDto);

        Assertions.assertNotNull(registerResult.getData());
    }
}