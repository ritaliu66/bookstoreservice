package com.epam.bookstoreservice.mapper;

import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * mapper for BookDTO and BookEntity
 */
@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookDtoAndBookEntityMapper {

    BookEntity requestDtoToEntity(BookRequestDTO bookRequestDto);

    BookResponseDTO entityToResponseDto(BookEntity entity);

    void toEntity(@MappingTarget BookEntity bookEntity,BookRequestDTO bookRequestDTO);

}
