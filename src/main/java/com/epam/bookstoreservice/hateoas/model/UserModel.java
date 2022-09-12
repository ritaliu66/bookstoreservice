package com.epam.bookstoreservice.hateoas.model;

import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

/**
 * the model for userResponseDTO
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserModel extends RepresentationModel<UserModel> {

    private final UserResponseDTO content;

    @JsonCreator
    public UserModel(@JsonProperty("content") UserResponseDTO userResponseDTO) {
        this.content = userResponseDTO;
    }

}
