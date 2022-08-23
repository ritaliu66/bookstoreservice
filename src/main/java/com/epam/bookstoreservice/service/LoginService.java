package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
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
   String loginAndReturnToken(UserRequestDTO userRequestDto);


}
