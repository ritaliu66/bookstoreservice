package com.epam.bookstoreservice.hateoas.assembler;

import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.hateoas.model.UserModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * model assembler for userResponseDTO
 */
@Component
public class UserResponseDTOAssembler implements RepresentationModelAssembler<UserResponseDTO,UserModel> {
    @Override
    public UserModel toModel(UserResponseDTO userResponseDTO) {
        return new UserModel(userResponseDTO);
    }
}
