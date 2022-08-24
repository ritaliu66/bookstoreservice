package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
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
   UserResponseDTO register(UserRequestDTO userEntity);
}
