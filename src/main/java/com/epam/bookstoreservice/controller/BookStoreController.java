package com.epam.bookstoreservice.controller;


import com.epam.bookstoreservice.hateoas.assembler.BookResponseDTOAssembler;
import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.hateoas.model.BookModel;
import com.epam.bookstoreservice.hateoas.model.IntegerModel;
import com.epam.bookstoreservice.service.BookstoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * controller for bookstore
 */
@RestController
@RequestMapping("/v1/bookstore")
@AllArgsConstructor
@Tag(name = "bookstore")
public class BookStoreController {

    private final BookstoreService bookstoreService;

    private final BookResponseDTOAssembler bookInfoAssembler;

    @PostMapping("/add-new-book")
    @Operation(description = "add new book")
    public ResponseEntity<BookModel> addNewBook(BookRequestDTO bookRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookInfoAssembler.toModel(bookstoreService.addNewBook(bookRequestDTO)));
    }

    @PostMapping("/add-book")
    @Operation(description = "add existed book")
    public ResponseEntity<BookModel> addExistentBook(BookRequestDTO bookRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookInfoAssembler.toModel(bookstoreService.addExistentBook(bookRequestDTO)));
    }

    @GetMapping("/book/{id}")
    @Operation(description = "get book by id")
    public ResponseEntity<BookModel> getBookById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookInfoAssembler.toModel(bookstoreService.getBookById(id)));
    }

    @GetMapping("/book-list")
    @Operation(description = "get all books")
    public ResponseEntity<CollectionModel<BookModel>> getAllBooks() {
        CollectionModel<BookModel> bookModels = bookInfoAssembler.toCollectionModel(bookstoreService.getAllBooks());
        bookModels.add(linkTo(methodOn(BookStoreController.class).getAllBooks()).withSelfRel());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookModels);

    }


    @GetMapping("/number-of-books/{id}")
    @Operation(description = "get number of books available by id")
    public ResponseEntity<IntegerModel> getNumberOfBooksAvailableById(@PathVariable Integer id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getIntegerModel(bookstoreService.getNumberOfBooksAvailableById(id)
                        , methodOn(BookStoreController.class).getNumberOfBooksAvailableById(id)));
    }

    @PostMapping("/sell-book/{id}")
    @Operation(description = "sell a book")
    public ResponseEntity<BookModel> sellABook(@PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookInfoAssembler.toModel(bookstoreService.sellABook(id)));
    }

    @PostMapping("/sell-books")
    @Operation(description = "sell list of books")
    public ResponseEntity<CollectionModel<BookModel>> sellListOfBooks(@RequestBody List<SellDTO> sellDTOList) {
        CollectionModel<BookModel> bookModels
                = bookInfoAssembler.toCollectionModel(bookstoreService.sellListOfBooks(sellDTOList));
        bookModels
                .add(linkTo(methodOn(BookStoreController.class)
                        .sellListOfBooks(sellDTOList)).withSelfRel());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookModels);
    }

    @PutMapping("/update-book/{id}")
    @Operation(description = "update a book")
    public ResponseEntity<BookModel> updateABook(@PathVariable Integer id, BookRequestDTO bookRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookInfoAssembler.toModel(bookstoreService.updateABook(id, bookRequestDTO)));
    }

    @GetMapping("/books")
    @Operation(description = "get books by category and keyword")
    public ResponseEntity<CollectionModel<BookModel>> getBooksByCategoryAndKeyWord(String category, String keyword) {
        CollectionModel<BookModel> bookModels
                = bookInfoAssembler.toCollectionModel(bookstoreService.getBooksByCategoryAndKeyWord(category, keyword));
        bookModels
                .add(linkTo(methodOn(BookStoreController.class)
                        .getBooksByCategoryAndKeyWord(category, keyword)).withSelfRel());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookModels);
    }

    @GetMapping("/number-of-books")
    @Operation(description = "get number of books sold per category and keyword")
    public ResponseEntity<IntegerModel> getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getIntegerModel(bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword(category, keyword)
                        , methodOn(BookStoreController.class).getNumberOfBooksSoldPerCategoryAndKeyword(category, keyword)));
    }


    private IntegerModel getIntegerModel(Integer number, Object method) {
        return new IntegerModel(number).add(linkTo(method).withSelfRel());
    }
}
