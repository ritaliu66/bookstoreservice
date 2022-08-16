package com.epam.bookstoreservice.dao;

import com.epam.bookstoreservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * the dao for user
 */
@Repository
public interface UserDao extends JpaRepository<UserEntity,Integer> {


    UserEntity findByUsername(String username);

    UserEntity findByPhoneNumber(Integer phoneNumber);
}
