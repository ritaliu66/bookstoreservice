package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.dto.response.UserResponseDto;

/**
 * the service for user
 */
public interface UserService {

    /**
     * User registration
     * @param userEntity
     * @return
     */
    UserResponseDto register(UserRequestDto userEntity);
}
