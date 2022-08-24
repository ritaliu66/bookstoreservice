package com.epam.bookstoreservice.security.userdetailsservice;

import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.exception.PhoneNumberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws PhoneNumberNotFoundException {

        return userDao.findByPhoneNumber(username);

    }

}
