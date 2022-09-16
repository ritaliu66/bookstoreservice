package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.hateoas.assembler.TokenResponseDTOAssembler;
import com.epam.bookstoreservice.hateoas.model.TokenModel;
import com.epam.bookstoreservice.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@AllArgsConstructor
@Tag(name = "login")
public class LoginController {

    private final LoginService loginService;

    private final TokenResponseDTOAssembler tokenResponseDTOAssembler;

    @PostMapping("/token")
    @Operation(description  = "login and return a token")
    public ResponseEntity<TokenModel> loginAndReturnToken(UserRequestDTO userRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenResponseDTOAssembler
                        .toModel(loginService.loginAndReturnToken(userRequestDto),userRequestDto));

    }

}
