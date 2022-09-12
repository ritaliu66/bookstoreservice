package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.hateoas.assembler.UserResponseDTOAssembler;
import com.epam.bookstoreservice.hateoas.model.UserModel;
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

    private final UserService userService;

    private final UserResponseDTOAssembler userResponseDTOAssembler;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerAUser(UserRequestDTO userRequestDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseDTOAssembler.toModel(userService.registerAUser(userRequestDto))
                .add(linkTo(methodOn(UserController.class).registerAUser(userRequestDto)).withSelfRel()));
    }

}
