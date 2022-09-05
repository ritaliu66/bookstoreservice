package com.epam.bookstoreservice.dao;

import com.epam.bookstoreservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * dao for sys_user
 */
@Repository
public interface UserDao extends JpaRepository<UserEntity,Integer> {

    UserEntity findByPhoneNumber(String phoneNumber);
}
