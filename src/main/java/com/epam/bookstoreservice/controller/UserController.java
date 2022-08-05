package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * the controller for user service
 */
@RestController
@RequestMapping("/v1/user")
@Api("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ApiOperation("user register")
    public ResponseEntity<UserEntity> register(UserEntity userEntity){
        return ResponseEntity.ok(userService.register(userEntity));
    }

}
