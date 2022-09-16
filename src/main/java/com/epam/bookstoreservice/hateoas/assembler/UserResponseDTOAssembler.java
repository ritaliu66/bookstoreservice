package com.epam.bookstoreservice.hateoas.assembler;

import com.epam.bookstoreservice.controller.UserController;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.hateoas.model.UserModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * model assembler for userResponseDTO
 */
@Component
public class UserResponseDTOAssembler implements RepresentationModelAssembler<UserResponseDTO,UserModel> {
    @Override
    public UserModel toModel(UserResponseDTO userResponseDTO) {
        return new UserModel(userResponseDTO);
    }

    public UserModel toModel(UserResponseDTO userResponseDTO, UserRequestDTO userRequestDTO) {
        return toModel(userResponseDTO)
                .add(linkTo(methodOn(UserController.class)
                        .registerAUser(userRequestDTO)).withSelfRel());
    }

}
