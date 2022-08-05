package com.epam.bookstoreservice.controller;


import com.epam.bookstoreservice.dto.request.BookRequestDto;
import com.epam.bookstoreservice.dto.request.SellDto;
import com.epam.bookstoreservice.dto.response.BookResponseDto;
import com.epam.bookstoreservice.entity.BookEntity;
import com.epam.bookstoreservice.service.BookstoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("v1/bookstore")
@AllArgsConstructor
@Api
public class BookStoreController {

    private final BookstoreService bookstoreService;

    @PostMapping("/add-new-book")
    @ApiOperation("add new book")
    public ResponseEntity<BookResponseDto> addNewBook(BookRequestDto book) {

        BookResponseDto result = bookstoreService.addNewBook(book);

        return ResponseEntity.ok(result);
    }


    @PostMapping("/add-book")
    @ApiOperation("add existed book")
    public ResponseEntity<BookResponseDto> addBook(BookRequestDto book) {

        BookResponseDto result = bookstoreService.addBook(book);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/book/{id}")
    @ApiOperation("get book by id")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Integer id) {
        BookResponseDto bookById = bookstoreService.getBookById(id);
        return ResponseEntity.ok(bookById);
    }

    @GetMapping("/book-list")
    @ApiOperation("get all books")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> allBooks = bookstoreService.getAllBooks();
        return ResponseEntity.ok(allBooks);
    }


    @GetMapping("/number-of-books/{id}")
    @ApiOperation("get number of books available by id")
    public ResponseEntity<Integer> getNumberOfBooksAvailableById(@PathVariable Integer id) {
        Integer numberOfBooksAvailableById = bookstoreService.getNumberOfBooksAvailableById(id);
        return ResponseEntity.ok(numberOfBooksAvailableById);
    }

    @PostMapping("/sell-book/{id}")
    @ApiOperation("sell a book")
    public ResponseEntity<Boolean> sellABook(@PathVariable Integer id) {
        Boolean result = bookstoreService.sellABook(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sell-books")
    @ApiOperation("sell list of books")
    public ResponseEntity<Boolean> sellListOfBooks(@RequestBody List<SellDto> sellDtoList) {
        Boolean result = bookstoreService.sellListOfBooks(sellDtoList);
        return ResponseEntity.ok(result);
    }

}
