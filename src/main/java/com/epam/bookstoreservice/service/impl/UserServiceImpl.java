package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.mapper.UserDtoAndUserEntityMapper;
import com.epam.bookstoreservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * the service for user
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final UserDtoAndUserEntityMapper userDtoToUserEntityMapper;


    @Override
    public UserResponseDTO registerAUser(UserRequestDTO userRequestDto) {

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return userDtoToUserEntityMapper
                .entityToResponseDto(userDao.save(userDtoToUserEntityMapper.requestDtoToEntity(userRequestDto)));

    }
}
