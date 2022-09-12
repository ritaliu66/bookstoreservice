package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.hateoas.assembler.BookResponseDTOAssembler;
import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.hateoas.model.BookModel;
import com.epam.bookstoreservice.hateoas.model.IntegerModel;
import com.epam.bookstoreservice.service.impl.BookstoreServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

/**
 * unit test for bookstore controller
 */
@SpringBootTest(classes = BookstoreServiceApplication.class)
class BookStoreControllerTest {

    @InjectMocks
    private BookStoreController bookStoreController;

    @Mock
    private BookstoreServiceImpl bookstoreService;

    @Autowired
    private BookResponseDTOAssembler bookInfoAssembler;


    private static final BigDecimal PRICE = new BigDecimal("54.5");

    private static final BigDecimal NEW_PRICE = new BigDecimal("50");

    private static final BookRequestDTO BOOK_REQUEST_DTO = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100);

    private static final BookRequestDTO UPDATE_BOOK_REQUEST = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookResponseDTO EXISTENT_BOOK_RESPONSE_DTO = new BookResponseDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 100);

    private static final BookResponseDTO NONEXISTENT_BOOK_RESPONSE_DTO = new BookResponseDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100, 0);

    private static final BookResponseDTO UPDATE_BOOK_RESPONSE_DTO = new BookResponseDTO(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100, 0);

    private static final Integer ID = 1;

    private static final List<BookResponseDTO> BOOK_ENTITY_LIST = new ArrayList<>();

    private static final SellDTO SELL_DTO = new SellDTO(1, 2);


    private static final String AUTHOR = "Jane";

    @BeforeEach
    public void initController() {
        bookStoreController = new BookStoreController(bookstoreService,bookInfoAssembler);
    }

    @Test
    void addNewBook() {
        Mockito.when(bookstoreService.addNewBook(any()))
                .thenReturn(NONEXISTENT_BOOK_RESPONSE_DTO);

        ResponseEntity<BookModel> result = bookStoreController.addNewBook(BOOK_REQUEST_DTO);

        Assertions.assertEquals(AUTHOR, Objects.requireNonNull(result.getBody()).getContent().getAuthor());

    }

    @Test
    void addBook() {
        Mockito.when(bookstoreService.addExistentBook(any()))
                .thenReturn(EXISTENT_BOOK_RESPONSE_DTO);

        ResponseEntity<BookModel> result = bookStoreController.addExistentBook(BOOK_REQUEST_DTO);
        Assertions.assertNotNull(Objects.requireNonNull(result.getBody()).getContent());
    }

    @Test
    void getBookById() {
        Mockito.when(bookstoreService.getBookById(any())).thenReturn(EXISTENT_BOOK_RESPONSE_DTO);
        ResponseEntity<BookModel> bookById = bookStoreController.getBookById(ID);
        Assertions.assertNotNull(Objects.requireNonNull(bookById.getBody()).getContent());
    }


    @Test
    void getAllBooks() {
        List<BookResponseDTO> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_RESPONSE_DTO);
        Mockito.when(bookstoreService.getAllBooks()).thenReturn(bookEntityList);
        ResponseEntity<CollectionModel<BookModel>> allBooks = bookStoreController.getAllBooks();
        Assertions.assertFalse(Objects.requireNonNull(allBooks.getBody()).getContent().isEmpty());

    }

    @Test
    void getNumberOfBooksAvailableByIdSuccessfully() {

        Mockito.when(bookstoreService.getNumberOfBooksAvailableById(any())).thenReturn(11);
        ResponseEntity<IntegerModel> result = bookStoreController.getNumberOfBooksAvailableById(ID);
        Assertions.assertNotNull(Objects.requireNonNull(result.getBody()).getContent());
    }


    @Test
    void sellABookSuccessfully() {
        Mockito.when(bookstoreService.sellABook(any())).thenReturn(EXISTENT_BOOK_RESPONSE_DTO);
        ResponseEntity<BookModel> result = bookStoreController.sellABook(ID);
        Assertions.assertNotNull(Objects.requireNonNull(result.getBody()).getContent());
    }


    @Test
    void sellListOfBooks() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_RESPONSE_DTO);

        List<SellDTO> sellDTOList = new ArrayList<>();
        sellDTOList.add(SELL_DTO);

        Mockito.when(bookstoreService.sellListOfBooks(any())).thenReturn(BOOK_ENTITY_LIST);
        ResponseEntity<CollectionModel<BookModel>> result = bookStoreController.sellListOfBooks(sellDTOList);
        Assertions.assertFalse(Objects.requireNonNull(result.getBody()).getContent().isEmpty());
    }


    @Test
    void updateABook() {

        Mockito.when(bookstoreService.updateABook(any(), any())).thenReturn(UPDATE_BOOK_RESPONSE_DTO);
        ResponseEntity<BookModel> result = bookStoreController.updateABook(ID, UPDATE_BOOK_REQUEST);
        Assertions.assertEquals(NEW_PRICE, Objects.requireNonNull(result.getBody()).getContent().getPrice());

    }


    @Test
    void getBooksByCategoryAndKeyWordSuccessfully() {
        List<BookResponseDTO> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_RESPONSE_DTO);

        Mockito.when(bookstoreService.getBooksByCategoryAndKeyWord(any(), any())).thenReturn(bookEntityList);
        ResponseEntity<CollectionModel<BookModel>> result = bookStoreController.getBooksByCategoryAndKeyWord("literary works", "1");
        Assertions.assertFalse(Objects.requireNonNull(result.getBody()).getContent().isEmpty());
    }


    @Test
    void getNumberOfBooksSoldPerCategoryAndKeywordSuccessfully() {

        Mockito.when(bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword(any(), any())).thenReturn(1000);
        ResponseEntity<IntegerModel> result = bookStoreController.getNumberOfBooksSoldPerCategoryAndKeyword("literary works", "1");
        Assertions.assertNotNull(Objects.requireNonNull(result.getBody()).getContent());
    }


}
