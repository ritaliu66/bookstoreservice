package com.epam.bookstoreservice.mapper;


import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * mapper for UserDTO and UserEntity
 */
@Mapper(componentModel = "spring")
public interface UserDtoAndUserEntityMapper {

    UserEntity requestDtoToEntity(UserRequestDTO userRequestDto);

    UserResponseDTO entityToResponseDto(UserEntity userEntity);

}
