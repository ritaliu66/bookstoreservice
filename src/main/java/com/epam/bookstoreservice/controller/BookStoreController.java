package com.epam.bookstoreservice.controller;


import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.service.BookstoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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

/**
 * controller for bookstore
 */
@RestController
@RequestMapping("/v1/bookstore")
@AllArgsConstructor
@Api
public class BookStoreController {

    private final BookstoreService bookstoreService;

    @PostMapping("/add-new-book")
    @ApiOperation("add new book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDTO> addNewBook(BookRequestDTO book) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.addNewBook(book));

    }


    @PostMapping("/add-book")
    @ApiOperation("add existed book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDTO> addExistentBook(BookRequestDTO book) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.addExistentBook(book));
    }

    @GetMapping("/book/{id}")
    @ApiOperation("get book by id")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Integer id) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.getBookById(id));

    }

    @GetMapping("/book-list")
    @ApiOperation("get all books")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.getAllBooks());

    }


    @GetMapping("/number-of-books/{id}")
    @ApiOperation("get number of books available by id")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<Integer> getNumberOfBooksAvailableById(@PathVariable Integer id) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.getNumberOfBooksAvailableById(id));
    }

    @PostMapping("/sell-book/{id}")
    @ApiOperation("sell a book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDTO> sellABook(@PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK).body(bookstoreService.sellABook(id));

    }

    @PostMapping("/sell-books")
    @ApiOperation("sell list of books")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<List<BookResponseDTO>> sellListOfBooks(@RequestBody List<SellDTO> sellDTOList) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.sellListOfBooks(sellDTOList));

    }

    @PutMapping("/update-book/{id}")
    @ApiOperation("update a book")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<BookResponseDTO> updateABook(@PathVariable Integer id, BookRequestDTO book) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.updateABook(id, book));

    }

    @GetMapping("/books")
    @ApiOperation("get books by category and keyword")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<List<BookResponseDTO>> getBooksByCategoryAndKeyWord(String category, String keyword) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.getBooksByCategoryAndKeyWord(category, keyword));

    }

    @GetMapping("/number-of-books")
    @ApiOperation("get number of books sold per category and keyword")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header",defaultValue = "Bearer ",dataType = "String", name = "Authorization", required = true) })
    public ResponseEntity<Integer> getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {

        return ResponseEntity
                .status(HttpStatus.OK).body(bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword(category, keyword));

    }
}
