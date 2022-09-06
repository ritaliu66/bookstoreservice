package com.epam.bookstoreservice.controller;


import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.model.BookModel;
import com.epam.bookstoreservice.model.IntegerModel;
import com.epam.bookstoreservice.service.BookstoreService;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * controller for bookstore
 */
@RestController
@RequestMapping("/v1/bookstore")
@AllArgsConstructor
public class BookStoreController {

    private final BookstoreService bookstoreService;

    @PostMapping("/add-new-book")
    public ResponseEntity<BookModel> addNewBook(BookRequestDTO bookRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModel(bookstoreService.addNewBook(bookRequestDTO)
                        ,methodOn(BookStoreController.class).addNewBook(bookRequestDTO)));
    }

    @PostMapping("/add-book")
    public ResponseEntity<BookModel> addExistentBook(BookRequestDTO bookRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModel(bookstoreService.addExistentBook(bookRequestDTO)
                        ,methodOn(BookStoreController.class).addExistentBook(bookRequestDTO)));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModel(bookstoreService.getBookById(id)
                        ,methodOn(BookStoreController.class).getBookById(id)));
    }

    @GetMapping("/book-list")
    public ResponseEntity<List<BookModel>> getAllBooks() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModelList(bookstoreService.getAllBooks()
                        ,methodOn(BookStoreController.class).getAllBooks()));

    }


    @GetMapping("/number-of-books/{id}")
    public ResponseEntity<IntegerModel> getNumberOfBooksAvailableById(@PathVariable Integer id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getIntegerModel(bookstoreService.getNumberOfBooksAvailableById(id)
                ,methodOn(BookStoreController.class).getNumberOfBooksAvailableById(id)));
    }

    @PostMapping("/sell-book/{id}")
    public ResponseEntity<BookModel> sellABook(@PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(getBookModel(bookstoreService.sellABook(id)
                ,methodOn(BookStoreController.class).sellABook(id)));
    }

    @PostMapping("/sell-books")
    public ResponseEntity<List<BookModel>> sellListOfBooks(@RequestBody List<SellDTO> sellDTOList) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModelList(bookstoreService.sellListOfBooks(sellDTOList)
                        ,methodOn(BookStoreController.class).sellListOfBooks(sellDTOList)));
    }

    @PutMapping("/update-book/{id}")
    public ResponseEntity<BookModel> updateABook(@PathVariable Integer id, BookRequestDTO bookRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModel(bookstoreService.updateABook(id,bookRequestDTO)
                        ,methodOn(BookStoreController.class).updateABook(id,bookRequestDTO)));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookModel>> getBooksByCategoryAndKeyWord(String category, String keyword) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getBookModelList(bookstoreService.getBooksByCategoryAndKeyWord(category, keyword)
                        ,methodOn(BookStoreController.class).getBooksByCategoryAndKeyWord(category,keyword)));
    }

    @GetMapping("/number-of-books")
    public ResponseEntity<IntegerModel> getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getIntegerModel(bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword(category, keyword)
                ,methodOn(BookStoreController.class).getNumberOfBooksSoldPerCategoryAndKeyword(category, keyword)));
    }

    private BookModel getBookModel(BookResponseDTO bookResponseDTO, Object method) {
        return new BookModel(bookResponseDTO).add(linkTo(method).withSelfRel());
    }

    private List<BookModel> getBookModelList(List<BookResponseDTO> bookResponseDTOList, Object method){
        return bookResponseDTOList
               .stream()
               .map(bookResponseDTO -> getBookModel(bookResponseDTO, method)).collect(Collectors.toList());
    }

    private IntegerModel getIntegerModel(Integer number,Object method){
        return new IntegerModel(number).add(linkTo(method).withSelfRel());
    }
}
