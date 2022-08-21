package com.epam.bookstoreservice.controller;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.service.impl.BookstoreServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = BookstoreServiceApplication.class)
class BookStoreControllerTest {

    @InjectMocks
    private BookStoreController bookStoreController;

    @Mock
    private BookstoreServiceImpl bookstoreService;


    private static final BigDecimal PRICE = new BigDecimal(54.5);

    private static final BigDecimal NEW_PRICE = new BigDecimal(50);

    private static final BookRequestDTO BOOK_REQUEST_DTO = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100);

    private static final BookRequestDTO UPDATE_BOOK_REQUEST = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookResponseDTO EXISTENT_BOOK_RESPONSE_DTO = new BookResponseDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 100);

    private static final BookResponseDTO NONEXISTENT_BOOK_RESPONSE_DTO = new BookResponseDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100, 0);

    private static final BookResponseDTO UPDATE_BOOK_RESPONSE_DTO = new BookResponseDTO(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100, 0);

    private static final Integer ID = 1;

    private static final List<BookResponseDTO> BOOK_ENTITY_LIST = new ArrayList<>();

    private static final SellDTO SELL_DTO = new SellDTO(1, 2);

    private static final String SUCCESSFUL_MESSAGE = "successful";

    private static final String AUTHOR="Jane";

    @BeforeEach
    public void initController() {
        bookStoreController = new BookStoreController(bookstoreService);
    }

    @Test
    void addNewBook() {
        Mockito.when(bookstoreService.addNewBook(any()))
                .thenReturn(Result.success(SUCCESSFUL_MESSAGE, NONEXISTENT_BOOK_RESPONSE_DTO));

        ResponseEntity<Result<BookResponseDTO>> result = bookStoreController.addNewBook(BOOK_REQUEST_DTO);

        BookResponseDTO resultData = (BookResponseDTO) result.getBody().getData();

        Assertions.assertEquals(AUTHOR, resultData.getAuthor());

    }

    @Test
    void addBook() {
        Mockito.when(bookstoreService.addBook(any()))
                .thenReturn(Result.success(SUCCESSFUL_MESSAGE, EXISTENT_BOOK_RESPONSE_DTO));

        ResponseEntity<Result<BookResponseDTO>> result = bookStoreController.addBook(BOOK_REQUEST_DTO);
        Assertions.assertNotNull(result.getBody().getData());
    }

    @Test
    void getBookById() {
        Mockito.when(bookstoreService.getBookById(any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, EXISTENT_BOOK_RESPONSE_DTO));
        ResponseEntity<Result<BookResponseDTO>> bookById = bookStoreController.getBookById(ID);
        Assertions.assertNotNull(bookById.getBody().getData());
    }


    @Test
    void getAllBooks() {
        List<BookResponseDTO> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_RESPONSE_DTO);
        Mockito.when(bookstoreService.getAllBooks()).thenReturn(Result.success(SUCCESSFUL_MESSAGE, bookEntityList));
        ResponseEntity<Result<List<BookResponseDTO>>> result = bookStoreController.getAllBooks();
        List<BookResponseDTO> allBooks = (List<BookResponseDTO>) result.getBody().getData();
        Assertions.assertTrue(!allBooks.isEmpty());

    }

    @Test
    void getNumberOfBooksAvailableByIdSuccessfully() {

        Mockito.when(bookstoreService.getNumberOfBooksAvailableById(any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, 11));
        ResponseEntity<Result<Integer>> result = bookStoreController.getNumberOfBooksAvailableById(ID);
        Assertions.assertNotNull(result.getBody().getData());
    }


    @Test
    void sellABookSuccessfully() {
        Mockito.when(bookstoreService.sellABook(any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, EXISTENT_BOOK_RESPONSE_DTO));
        ResponseEntity<Result<BookResponseDTO>> result = bookStoreController.sellABook(ID);
        Assertions.assertNotNull(result.getBody().getData());
    }


    @Test
    void sellListOfBooks() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_RESPONSE_DTO);

        List<SellDTO> sellDTOList = new ArrayList<>();
        sellDTOList.add(SELL_DTO);

        Mockito.when(bookstoreService.sellListOfBooks(any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, BOOK_ENTITY_LIST));
        ResponseEntity<Result<List<BookResponseDTO>>> result = bookStoreController.sellListOfBooks(sellDTOList);
        List<BookResponseDTO> bookResponseDTOList = (List<BookResponseDTO>) result.getBody().getData();
        Assertions.assertTrue(!bookResponseDTOList.isEmpty());
    }


    @Test
    void updateABook() {

        Mockito.when(bookstoreService.updateABook(any(), any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, UPDATE_BOOK_RESPONSE_DTO));
        ResponseEntity<Result<BookResponseDTO>> result = bookStoreController.updateABook(ID, UPDATE_BOOK_REQUEST);
        BookResponseDTO bookResponseDTO = (BookResponseDTO) result.getBody().getData();
        Assertions.assertEquals(NEW_PRICE, bookResponseDTO.getPrice());

    }


    @Test
    void getBooksByCategoryAndKeyWordSuccessfully() {
        List<BookResponseDTO> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_RESPONSE_DTO);

        Mockito.when(bookstoreService.getBooksByCategoryAndKeyWord(any(), any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, bookEntityList));
        ResponseEntity<Result<List<BookResponseDTO>>> result = bookStoreController.getBooksByCategoryAndKeyWord("literary works", "1");
        List<BookResponseDTO> booksByCategoryAndKeyWord = (List<BookResponseDTO>) result.getBody().getData();
        Assertions.assertTrue(!booksByCategoryAndKeyWord.isEmpty());
    }


    @Test
    void getNumberOfBooksSoldPerCategoryAndKeywordSuccessfully() {

        Mockito.when(bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword(any(), any())).thenReturn(Result.success(SUCCESSFUL_MESSAGE, 1000));
        ResponseEntity<Result<Integer>> result = bookStoreController.getNumberOfBooksSoldPerCategoryAndKeyword("literary works", "1");
        Integer numberOfBooksSoldPerCategoryAndKeyword = (Integer) result.getBody().getData();
        Assertions.assertNotNull(numberOfBooksSoldPerCategoryAndKeyword);
    }


}
