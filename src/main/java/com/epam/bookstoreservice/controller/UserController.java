package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    private static final String SUCCESSFUL_MESSAGE = "successful";

    @PostMapping("/register")
    @ApiOperation("user register")
    public ResponseEntity<Result<UserResponseDTO>> register(UserRequestDTO userRequestDto){
        return ResponseEntity.ok(Result.success(SUCCESSFUL_MESSAGE,userService.register(userRequestDto)));
    }

}
