package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import org.springframework.stereotype.Service;

/**
 * the service for user login
 */
@Service
public interface LoginService {

    /**
     * Log in and return the token
     *
     * @param userRequestDto
     * @return
     */
   TokenResponseDTO loginAndReturnToken(UserRequestDTO userRequestDto);


}
