package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * the controller for login
 */
@RestController
@RequestMapping("/v1/login")
@Api("user login")
@AllArgsConstructor
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final String SUCCESSFUL_MESSAGE = "successful";

    @PostMapping("/token")
    @ApiOperation(value = "get a token")
    public ResponseEntity<Result<String>> loginAndReturnToken(UserRequestDTO userRequestDto) {

        return ResponseEntity.ok(Result.success(SUCCESSFUL_MESSAGE,loginService.loginAndReturnToken(userRequestDto)));
    }

}
