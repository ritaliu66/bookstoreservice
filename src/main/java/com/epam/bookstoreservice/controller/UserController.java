package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("user register")
    public ResponseEntity<UserResponseDTO> registerAUser(UserRequestDTO userRequestDto){
        return ResponseEntity
                .status(HttpStatus.OK).body(userService.registerAUser(userRequestDto));
    }

}
