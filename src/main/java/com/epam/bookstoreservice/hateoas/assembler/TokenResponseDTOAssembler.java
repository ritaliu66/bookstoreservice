package com.epam.bookstoreservice.hateoas.assembler;

import com.epam.bookstoreservice.controller.LoginController;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.epam.bookstoreservice.hateoas.model.TokenModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * model assembler for tokenResponseDTO
 */
@Component
public class TokenResponseDTOAssembler implements RepresentationModelAssembler<TokenResponseDTO, TokenModel> {
    @Override
    public TokenModel toModel(TokenResponseDTO tokenResponseDTO) {
        return new TokenModel(tokenResponseDTO);
    }

    public TokenModel toModel(TokenResponseDTO tokenResponseDTO, UserRequestDTO userRequestDTO) {
        return toModel(tokenResponseDTO)
                .add(linkTo(methodOn(LoginController.class)
                        .loginAndReturnToken(userRequestDTO)).withSelfRel());
    }
}
