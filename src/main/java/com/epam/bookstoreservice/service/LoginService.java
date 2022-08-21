package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
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
    Result<String> loginAndReturnToken(UserRequestDTO userRequestDto);


}
