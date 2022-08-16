package com.epam.bookstoreservice.mapper;


import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.dto.response.UserResponseDto;
import com.epam.bookstoreservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoToUserEntityMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserEntity requestDtoToEntity(UserRequestDto userRequestDto);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserResponseDto entityToResponseDto(UserEntity userEntity);
}
