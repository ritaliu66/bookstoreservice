package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
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

    private static final String SUCCESSFUL_MESSAGE = "successful";

    @Override
    public Result<UserResponseDTO> register(UserRequestDTO userRequestDto) {
        String encodePassword = passwordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encodePassword);
        UserEntity userEntity = userDtoToUserEntityMapper.requestDtoToEntity(userRequestDto);
        return Result.success(SUCCESSFUL_MESSAGE, userDtoToUserEntityMapper.entityToResponseDto(userDao.save(userEntity)));

    }
}
