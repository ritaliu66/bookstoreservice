package com.epam.bookstoreservice.mapper;

import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.entity.BookEntity;
import org.mapstruct.Mapper;

/**
 * mapper for BookDTO and BookEntity
 */
@Mapper(componentModel = "spring")
public interface BookDtoAndBookEntityMapper {

    BookEntity requestDtoToEntity(BookRequestDTO bookRequestDto);

    BookResponseDTO entityToResponseDto(BookEntity entity);


}
