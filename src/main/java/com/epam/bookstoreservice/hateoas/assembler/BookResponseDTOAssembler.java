package com.epam.bookstoreservice.hateoas.assembler;

import com.epam.bookstoreservice.controller.BookStoreController;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.hateoas.model.BookModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * model assembler for BookResponseDTO
 */
@Component
public class BookResponseDTOAssembler implements RepresentationModelAssembler<BookResponseDTO, BookModel> {
    @Override
    public BookModel toModel(BookResponseDTO bookResponseDTO) {
        return new BookModel(bookResponseDTO)
                .add(linkTo(methodOn(BookStoreController.class)
                        .getBookById(bookResponseDTO.getId())).withSelfRel())
                .add(linkTo(methodOn(BookStoreController.class).getAllBooks()).withRel("books"));
    }

}
