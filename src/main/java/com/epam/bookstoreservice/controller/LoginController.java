package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.hateoas.assembler.TokenResponseDTOAssembler;
import com.epam.bookstoreservice.hateoas.model.TokenModel;
import com.epam.bookstoreservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * the controller for login
 */
@RestController
@RequestMapping("/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final TokenResponseDTOAssembler tokenResponseDTOAssembler;

    @PostMapping("/token")
    public ResponseEntity<TokenModel> loginAndReturnToken(UserRequestDTO userRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenResponseDTOAssembler
                        .toModel(loginService.loginAndReturnToken(userRequestDto))
                        .add(linkTo(methodOn(LoginController.class).loginAndReturnToken(userRequestDto)).withSelfRel()));

    }

}
