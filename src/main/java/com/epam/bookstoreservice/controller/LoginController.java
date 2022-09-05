package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.epam.bookstoreservice.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final LoginService loginService;

    @PostMapping("/token")
    @ApiOperation(value = "login and return a token")
    public ResponseEntity<TokenResponseDTO> loginAndReturnToken(UserRequestDTO userRequestDto) {

        return ResponseEntity
                .status(HttpStatus.OK).body(loginService.loginAndReturnToken(userRequestDto));

    }

}
