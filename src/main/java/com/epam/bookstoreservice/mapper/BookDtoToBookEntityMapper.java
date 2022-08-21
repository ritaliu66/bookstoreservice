package com.epam.bookstoreservice.mapper;

import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
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
    BookEntity requestDtoToEntity(BookRequestDTO bookRequestDto);

    @Mapping(target = "author", source = "author")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "totalCount", source = "totalCount")
    @Mapping(target = "sold", source = "sold")
    BookResponseDTO entityToResponseDto(BookEntity entity);


}
