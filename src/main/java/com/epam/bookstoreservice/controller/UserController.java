package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.model.UserModel;
import com.epam.bookstoreservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * the controller for user service
 */
@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerAUser(UserRequestDTO userRequestDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getUserModel(userService.registerAUser(userRequestDto)
                        ,methodOn(UserController.class).registerAUser(userRequestDto)));
    }

    private UserModel getUserModel(UserResponseDTO userResponseDTO,Object method){
        return new UserModel(userResponseDTO).add(linkTo(method).withSelfRel());
    }

}
