package com.epam.bookstoreservice.model;

import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@EqualsAndHashCode(callSuper = false)
public class TokenModel extends RepresentationModel<TokenModel> {

    private final TokenResponseDTO content;

    @JsonCreator
    public TokenModel(@JsonProperty("content") TokenResponseDTO tokenResponseDTO) {
        this.content = tokenResponseDTO;
    }
}
