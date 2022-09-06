package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.epam.bookstoreservice.model.TokenModel;
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

    @PostMapping("/token")
    public ResponseEntity<TokenModel> loginAndReturnToken(UserRequestDTO userRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getTokenModel(loginService.loginAndReturnToken(userRequestDto)
                ,methodOn(LoginController.class).loginAndReturnToken(userRequestDto)));

    }

    private TokenModel getTokenModel(TokenResponseDTO tokenResponseDTO, Object method) {
        return new TokenModel(tokenResponseDTO).add(linkTo(method).withSelfRel());
    }

}
