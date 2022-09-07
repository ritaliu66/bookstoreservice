package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dao.BookDao;
import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.entity.BookEntity;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.BookNotFoundException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import com.epam.bookstoreservice.mapper.BookDtoAndBookEntityMapper;

import com.epam.bookstoreservice.model.BookModel;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

/**
 * unit test for bookstore service
 */
@SpringBootTest(classes = BookstoreServiceApplication.class)
class BookstoreServiceImplTest {

    @InjectMocks
    private BookstoreServiceImpl bookstoreService;

    private final BookDtoAndBookEntityMapper bookRequestDtoToBookEntity = Mappers.getMapper(
            BookDtoAndBookEntityMapper.class);

    @Mock
    private BookDao bookDao;

    private static final BigDecimal PRICE = new BigDecimal("54.5");

    private static final BigDecimal NEW_PRICE = new BigDecimal("50");

    private static final BookRequestDTO BOOK_REQUEST_DTO = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100);

    private static final BookRequestDTO UPDATE_BOOK_REQUEST_DTO = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookRequestDTO NONEXISTENT_UPDATE_BOOK_REQUEST_DTO = new BookRequestDTO(2, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookEntity EXISTENT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 100);

    private static final BookEntity NONEXISTENT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100, 0);

    private static final BookEntity UPDATE_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100, 0);

    private static final BookEntity WRONG_UPDATE_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100, 0);

    private static final BookEntity SOLD_OUT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 0, 1000);

    private static final Integer BOOK_ID = 1;

    private static final Integer NEW_BOOK_ID = 2;

    private static final List<BookEntity> BOOK_ENTITY_LIST = new ArrayList<>();

    private static final SellDTO SELL_DTO = new SellDTO(1, 2);

    private static final String AUTHOR = "Jane";

    private static final Integer TOTAL_COUNT = 1100;


    @BeforeEach
    public void init() {
        bookstoreService = new BookstoreServiceImpl(bookRequestDtoToBookEntity, bookDao);
    }


    @Test
    void addNewBook() {
        Mockito.when(bookDao.save(any()))
                .thenReturn(NONEXISTENT_BOOK_ENTITY);

        Assertions.assertEquals(AUTHOR, bookstoreService.addNewBook(BOOK_REQUEST_DTO).getAuthor());

    }

    @Test
    void addBook() {
        Mockito.when(bookDao.findById(any()))
                .thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Mockito.when(bookDao.save(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);

        Assertions.assertEquals(TOTAL_COUNT, bookstoreService.addExistentBook(BOOK_REQUEST_DTO).getTotalCount());
    }

    @Test
    void getBookByIdSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));

        Assertions.assertNotNull(bookstoreService.getBookById(BOOK_ID));
    }

    @Test
    void getBookByIdUnsuccessfully() {

        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.getBookById(BOOK_ID);
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book can not be found in the bookstore", ex.getErrorMsg());
        }

    }

    @Test
    void getAllBooks() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findAll()).thenReturn(BOOK_ENTITY_LIST);

        Assertions.assertTrue(!bookstoreService.getAllBooks().isEmpty());

    }

    @Test
    void getNumberOfBooksAvailableByIdSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));

        Assertions.assertNotNull(bookstoreService.getNumberOfBooksAvailableById(BOOK_ID));
    }

    @Test
    void getNumberOfBooksAvailableByIdUnsuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.getNumberOfBooksAvailableById(BOOK_ID);
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book can not be found in the bookstore", ex.getErrorMsg());
        }
    }

    @Test
    void sellABookSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(EXISTENT_BOOK_ENTITY);

        Assertions.assertNotNull(bookstoreService.sellABook(BOOK_ID));
    }

    @Test
    void sellABookUnsuccessfullyByInsufficientInventory() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(SOLD_OUT_BOOK_ENTITY));
        try {
            bookstoreService.sellABook(BOOK_ID);
            Assertions.fail("Expected an InsufficientInventoryException to be thrown");
        } catch (InsufficientInventoryException ex) {
            Assertions.assertEquals("This book is out of stock", ex.getErrorMsg());
        }

    }

    @Test
    void sellABookUnsuccessfullyByNoSuchBook() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.sellABook(BOOK_ID);
            Assertions.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assertions.assertEquals("This book can not be found in the bookstore", ex.getErrorMsg());
        }

    }

    @Test
    void sellListOfBooksSuccessfully() {


        List<SellDTO> sellDTOList = new ArrayList<>();
        sellDTOList.add(SELL_DTO);

        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));

        Assertions.assertFalse(bookstoreService.sellListOfBooks(sellDTOList).isEmpty());
    }

    @Test
    void sellListOfBooksUnsuccessfully() {

        List<SellDTO> sellDTOList = new ArrayList<>();
        sellDTOList.add(SELL_DTO);

        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(SOLD_OUT_BOOK_ENTITY));
        try {
            bookstoreService.sellListOfBooks(sellDTOList);
            Assertions.fail("Expected an InsufficientInventoryException to be thrown");
        } catch (InsufficientInventoryException ex) {
            Assertions.assertEquals("This book is out of stock", ex.getErrorMsg());
        }
    }

    @Test
    void updateABookSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(UPDATE_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(UPDATE_BOOK_ENTITY);

        Assertions.assertEquals(NEW_PRICE, bookstoreService.updateABook(BOOK_ID, UPDATE_BOOK_REQUEST_DTO).getPrice());

    }

    @Test
    void updateABookUnsuccessfullyByUnmatchedId() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(WRONG_UPDATE_BOOK_ENTITY));
        try {
            bookstoreService.updateABook(NEW_BOOK_ID, UPDATE_BOOK_REQUEST_DTO);

            Assertions.fail("Expected an UnmatchedIdException to be thrown");
        } catch (UnmatchedIdException ex) {
            Assertions.assertEquals("The id in the BookDTO does not match the path variable id", ex.getErrorMsg());
        }

    }

    @Test
    void updateABookUnsuccessfullyByNoSuchBook() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.updateABook(NEW_BOOK_ID, NONEXISTENT_UPDATE_BOOK_REQUEST_DTO);

            Assertions.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assertions.assertEquals("This book can not be found in the bookstore", ex.getErrorMsg());
        }

    }

    @Test
    void getBooksByCategoryAndKeyWordSuccessfully() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);

        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(bookEntityList);

        Assertions.assertFalse(bookstoreService.getBooksByCategoryAndKeyWord("literary works", "1").isEmpty());
    }


    @Test
    void getBooksByCategoryAndKeyWordUnsuccessfullyByNoSuchBook() {
        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(new ArrayList<>());
        try {
            bookstoreService.getBooksByCategoryAndKeyWord("literary works", "wrong");
            Assertions.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assertions.assertEquals("This book can not be found in the bookstore", ex.getErrorMsg());
        }
    }


    @Test
    void getNumberOfBooksSoldPerCategoryAndKeywordSuccessfully() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);

        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(bookEntityList);

        Assertions.assertNotNull(bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword("literary works", "1"));
    }


    @Test
    void getNumberOfBooksSoldPerCategoryAndKeywordByNoSuchBook() {
        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(new ArrayList<>());
        try {
            bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword("wrong", "Jane");
            Assertions.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assertions.assertEquals("This book can not be found in the bookstore", ex.getErrorMsg());
        }
    }


}