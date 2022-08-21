package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;

/**
 * the service for user
 */
public interface UserService {

    /**
     * User registration
     *
     * @param userEntity
     * @return
     */
    Result<UserResponseDTO> register(UserRequestDTO userEntity);
}
