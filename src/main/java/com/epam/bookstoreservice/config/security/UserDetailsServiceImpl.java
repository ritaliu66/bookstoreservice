package com.epam.bookstoreservice.config.security;

import com.epam.bookstoreservice.dao.UserDao;
import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.exception.PhoneNumberNotFoundException;
import com.epam.bookstoreservice.model.LoginUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserEntity userEntity = userDao.findByUsername(username);
            UserDetail userDetail = new UserDetail();
            BeanUtils.copyProperties(userEntity, userDetail);
            return userDetail;
    }

    public LoginUserDetails loadUserByPhoneNumber(Integer phoneNumber) throws PhoneNumberNotFoundException {
        UserEntity userEntity=userDao.findByPhoneNumber(phoneNumber);
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        BeanUtils.copyProperties(userEntity,loginUserDetails);
        return loginUserDetails;
    }
}
