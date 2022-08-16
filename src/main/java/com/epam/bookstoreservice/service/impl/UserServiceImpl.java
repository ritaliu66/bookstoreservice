package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.dto.response.UserResponseDto;
import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.mapper.UserDtoToUserEntityMapper;
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

    private final UserDtoToUserEntityMapper userDtoToUserEntityMapper;

    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        String encodePassword = passwordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encodePassword);
        UserEntity userEntity = userDtoToUserEntityMapper.requestDtoToEntity(userRequestDto);
        UserEntity entity = userDao.save(userEntity);
        return userDtoToUserEntityMapper.entityToResponseDto(userEntity);

    }
}
