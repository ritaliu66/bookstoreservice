package com.epam.bookstoreservice.mapper;

import com.epam.bookstoreservice.dto.request.BookRequestDto;
import com.epam.bookstoreservice.dto.response.BookResponseDto;
import com.epam.bookstoreservice.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookDtoToBookEntityMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "totalCount", source = "totalCount")
    BookEntity requestDtoToEntity(BookRequestDto person);

    @Mapping(target = "author", source = "author")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "totalCount", source = "totalCount")
    @Mapping(target = "sold", source = "sold")
    BookResponseDto entityToResponseDto(BookEntity entity);


}
