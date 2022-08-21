package com.epam.bookstoreservice.mapper;


import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.dto.response.UserResponseDTO;
import com.epam.bookstoreservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoToUserEntityMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserEntity requestDtoToEntity(UserRequestDTO userRequestDto);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserResponseDTO entityToResponseDto(UserEntity userEntity);
}
