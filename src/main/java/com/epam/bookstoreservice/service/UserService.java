package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.entity.UserEntity;

/**
 * the service for user
 */
public interface UserService {

    /**
     * User registration
     * @param userEntity
     * @return
     */
    UserEntity register(UserEntity userEntity);
}
