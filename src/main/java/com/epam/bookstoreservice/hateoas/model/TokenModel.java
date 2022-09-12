package com.epam.bookstoreservice.hateoas.model;

import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

/**
 * the model for tokenResponseDTO
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class TokenModel extends RepresentationModel<TokenModel> {

    private final TokenResponseDTO content;

    @JsonCreator
    public TokenModel(@JsonProperty("content") TokenResponseDTO tokenResponseDTO) {
        this.content = tokenResponseDTO;
    }
}
