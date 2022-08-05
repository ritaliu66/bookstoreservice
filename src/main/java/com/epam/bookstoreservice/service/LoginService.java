package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.config.security.UserDetail;
import com.epam.bookstoreservice.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * the service for user login
 */
@Service
public interface LoginService {

    /**
     * Log in and return the token
     *
     * @param userEntity
     * @return
     */
    String login(UserEntity userEntity);


}
