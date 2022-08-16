package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * the controller for login
 */
@RestController
@RequestMapping("/v1/login")
@Api("user login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/token")
    @ApiOperation(value = "get a token")
    @CrossOrigin
    public ResponseEntity<String> login(UserRequestDto userRequestDto) {
        String token = loginService.login(userRequestDto);
        return ResponseEntity.ok(token);
    }

}
