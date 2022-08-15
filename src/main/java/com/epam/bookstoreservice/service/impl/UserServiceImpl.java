package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.entity.UserEntity;
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

    @Override
    public UserEntity register(UserEntity userEntity) {
        String encodePassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodePassword);
        return userDao.save(userEntity);
    }
}
