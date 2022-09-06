package com.epam.bookstoreservice.model;

import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@EqualsAndHashCode(callSuper = false)
public class BookModel extends RepresentationModel<BookModel> {

    private final BookResponseDTO content;

    @JsonCreator
    public BookModel(@JsonProperty("content") BookResponseDTO bookResponseDTO) {
        this.content = bookResponseDTO;
    }
}
