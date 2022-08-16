package com.epam.bookstoreservice.controller;


import com.epam.bookstoreservice.dto.request.BookRequestDto;
import com.epam.bookstoreservice.dto.request.SellDto;
import com.epam.bookstoreservice.dto.response.BookResponseDto;
import com.epam.bookstoreservice.service.BookstoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/bookstore")
@AllArgsConstructor
@Api
public class BookStoreController {

    private final BookstoreService bookstoreService;

    @PostMapping("/add-new-book")
    @ApiOperation("add new book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDto> addNewBook(BookRequestDto book) {

        BookResponseDto result = bookstoreService.addNewBook(book);

        return ResponseEntity.ok(result);
    }


    @PostMapping("/add-book")
    @ApiOperation("add existed book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDto> addBook(BookRequestDto book) {

        BookResponseDto result = bookstoreService.addBook(book);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/book/{id}")
    @ApiOperation("get book by id")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Integer id) {
        BookResponseDto bookById = bookstoreService.getBookById(id);
        return ResponseEntity.ok(bookById);
    }

    @GetMapping("/book-list")
    @ApiOperation("get all books")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> allBooks = bookstoreService.getAllBooks();
        return ResponseEntity.ok(allBooks);
    }


    @GetMapping("/number-of-books/{id}")
    @ApiOperation("get number of books available by id")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<Integer> getNumberOfBooksAvailableById(@PathVariable Integer id) {
        Integer numberOfBooksAvailableById = bookstoreService.getNumberOfBooksAvailableById(id);
        return ResponseEntity.ok(numberOfBooksAvailableById);
    }

    @PostMapping("/sell-book/{id}")
    @ApiOperation("sell a book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<Boolean> sellABook(@PathVariable Integer id) {
        Boolean result = bookstoreService.sellABook(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sell-books")
    @ApiOperation("sell list of books")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<Boolean> sellListOfBooks(@RequestBody List<SellDto> sellDtoList) {
        Boolean result = bookstoreService.sellListOfBooks(sellDtoList);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update-book/{id}")
    @ApiOperation("update a book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDto> updateABook(@PathVariable Integer id, BookRequestDto book) {
        BookResponseDto bookResponseDto = bookstoreService.updateABook(id, book);
        return ResponseEntity.ok(bookResponseDto);
    }

    @GetMapping("/books")
    @ApiOperation("get books by category and keyword")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<List<BookResponseDto>> getBooksByCategoryAndKeyWord(String category, String keyword) {
        List<BookResponseDto> booksByCategoryAndKeyWord = bookstoreService.getBooksByCategoryAndKeyWord(category, keyword);
        return ResponseEntity.ok(booksByCategoryAndKeyWord);
    }

    @GetMapping("/number-of-books")
    @ApiOperation("get number of books sold per category and keyword")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<Integer> getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {
        Integer numberOfBooksSoldPerCategoryAndKeyword = bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword(category, keyword);
        return ResponseEntity.ok(numberOfBooksSoldPerCategoryAndKeyword);
    }
}
