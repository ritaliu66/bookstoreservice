package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dao.BookDao;
import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.dto.response.Result;
import com.epam.bookstoreservice.entity.BookEntity;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.BookNotFoundException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import com.epam.bookstoreservice.mapper.BookDtoToBookEntityMapper;

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
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = BookstoreServiceApplication.class)
class BookstoreServiceImplTest {

    @InjectMocks
    private BookstoreServiceImpl bookstoreService;

    private final BookDtoToBookEntityMapper bookRequestDtoToBookEntity = Mappers.getMapper(
            BookDtoToBookEntityMapper.class);

    @Mock
    private BookDao bookDao;


    private static final BigDecimal PRICE = new BigDecimal(54.5);

    private static final BigDecimal NEW_PRICE = new BigDecimal(50);

    private static final BookRequestDTO BOOK_REQUEST_DTO = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100);

    private static final BookRequestDTO UPDATE_BOOK_REQUEST = new BookRequestDTO(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookRequestDTO NONEXISTENT_UPDATE_BOOK_REQUEST = new BookRequestDTO(2, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookEntity EXISTENT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 100);

    private static final BookEntity NONEXISTENT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100, 0);

    private static final BookEntity UPDATE_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100, 0);

    private static final BookEntity WRONG_UPDATE_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", NEW_PRICE, 100, 0);

    private static final BookEntity SOLD_OUT_BOOK = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 1000);

    private static final Integer ID = 1;

    private static final Integer NEW_ID = 2;

    private static final List<BookEntity> BOOK_ENTITY_LIST = new ArrayList<>();

    private static final SellDTO SELL_DTO = new SellDTO(1, 2);

    private static final String AUTHOR="Jane";

    private static final Integer TOTAL_COUNT=1100;


    @BeforeEach
    public void init() {
        bookstoreService = new BookstoreServiceImpl(bookRequestDtoToBookEntity, bookDao);
    }


    @Test
    void addNewBook() {
        Mockito.when(bookDao.save(any()))
                .thenReturn(NONEXISTENT_BOOK_ENTITY);

        Result<BookResponseDTO> result = bookstoreService.addNewBook(BOOK_REQUEST_DTO);

        BookResponseDTO resultData = (BookResponseDTO) result.getData();

        Assertions.assertEquals(AUTHOR, resultData.getAuthor());

    }

    @Test
    void addBook() {
        Mockito.when(bookDao.findByTitle(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.save(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);

        Result<BookResponseDTO> result = bookstoreService.addBook(BOOK_REQUEST_DTO);

        BookResponseDTO resultData = (BookResponseDTO) result.getData();

        Assertions.assertEquals(TOTAL_COUNT, resultData.getTotalCount());
    }

    @Test
    void getBookByIdSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));

        Result<BookResponseDTO> result = bookstoreService.getBookById(ID);
        BookResponseDTO resultData = (BookResponseDTO) result.getData();

        Assertions.assertNotNull(resultData);
    }

    @Test
    void getBookByIdUnsuccessfully() {

        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.getBookById(ID);
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book is not available in the bookstore", ex.getErrorMsg());
        }

    }

    @Test
    void getAllBooks() {
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findAll()).thenReturn(BOOK_ENTITY_LIST);

        Result<List<BookResponseDTO>> allBooks = bookstoreService.getAllBooks();
        List<BookResponseDTO> bookResponseDTOS = (List<BookResponseDTO>) allBooks.getData();
        Assertions.assertTrue(!bookResponseDTOS.isEmpty());

    }

    @Test
    void getNumberOfBooksAvailableByIdSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Result<Integer> result = bookstoreService.getNumberOfBooksAvailableById(ID);

        Integer numberOfBooksAvailableById = (Integer) result.getData();

        Assertions.assertNotNull(numberOfBooksAvailableById);
    }

    @Test
    void getNumberOfBooksAvailableByIdUnsuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            Result<Integer> result = bookstoreService.getNumberOfBooksAvailableById(ID);
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book is not available in the bookstore", ex.getErrorMsg());
        }
    }

    @Test
    void sellABookSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(EXISTENT_BOOK_ENTITY);

        Result<BookResponseDTO> result = bookstoreService.sellABook(ID);
        Assertions.assertNotNull(result.getData());
    }

    @Test
    void sellABookUnsuccessfullyByInsufficientInventory() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(SOLD_OUT_BOOK));
        try {
            bookstoreService.sellABook(ID);
            Assert.fail("Expected an InsufficientInventoryException to be thrown");
        } catch (InsufficientInventoryException ex) {
            Assert.assertEquals("This book is out of stock", ex.getErrorMsg());
        }

    }

    @Test
    void sellABookUnsuccessfullyByNoSuchBook() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.sellABook(ID);
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book is not available in the bookstore", ex.getErrorMsg());
        }

    }

    @Test
    void sellListOfBooksSuccessfully() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);

        List<SellDTO> sellDTOList = new ArrayList<>();
        sellDTOList.add(SELL_DTO);

        Mockito.when(bookDao.findAllById(any())).thenReturn(bookEntityList);
        Result<List<BookResponseDTO>> listResult = bookstoreService.sellListOfBooks(sellDTOList);
        List<BookResponseDTO> data = (List<BookResponseDTO>) listResult.getData();

        Assertions.assertTrue(!data.isEmpty());
    }

    @Test
    void sellListOfBooksUnsuccessfully() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(SOLD_OUT_BOOK);

        List<SellDTO> sellDTOList = new ArrayList<>();
        sellDTOList.add(SELL_DTO);

        Mockito.when(bookDao.findAllById(any())).thenReturn(bookEntityList);
        try {
            bookstoreService.sellListOfBooks(sellDTOList);
            Assert.fail("Expected an InsufficientInventoryException to be thrown");
        } catch (InsufficientInventoryException ex) {
            Assert.assertEquals("This book is out of stock", ex.getErrorMsg());
        }
    }

    @Test
    void updateABookSuccessfully() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(UPDATE_BOOK_ENTITY));
        Mockito.when(bookDao.save(any())).thenReturn(UPDATE_BOOK_ENTITY);
        Result<BookResponseDTO> result = bookstoreService.updateABook(ID, UPDATE_BOOK_REQUEST);

        BookResponseDTO resultData = (BookResponseDTO) result.getData();
        Assertions.assertEquals(PRICE, resultData.getPrice());

    }

    @Test
    void updateABookUnsuccessfullyByUnmatchedId() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(WRONG_UPDATE_BOOK_ENTITY));
        try {
            bookstoreService.updateABook(NEW_ID, UPDATE_BOOK_REQUEST);

            Assert.fail("Expected an UnmatchedIdException to be thrown");
        } catch (UnmatchedIdException ex) {
            Assert.assertEquals("The id in the BookDTO does not match the path variable id", ex.getErrorMsg());
        }

    }

    @Test
    void updateABookUnsuccessfullyByNoSuchBook() {
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.empty());
        try {
            bookstoreService.updateABook(NEW_ID, NONEXISTENT_UPDATE_BOOK_REQUEST);

            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book is not available in the bookstore", ex.getErrorMsg());
        }

    }

    @Test
    void getBooksByCategoryAndKeyWordSuccessfully() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);

        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(bookEntityList);
        Result<List<BookResponseDTO>> result = bookstoreService.getBooksByCategoryAndKeyWord("literary works", "1");
        List<BookResponseDTO> booksByCategoryAndKeyWord = (List<BookResponseDTO>) result.getData();
        Assertions.assertTrue(!booksByCategoryAndKeyWord.isEmpty());
    }


    @Test
    void getBooksByCategoryAndKeyWordUnsuccessfullyByNoSuchBook() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(new ArrayList<>());
        try {
            bookstoreService.getBooksByCategoryAndKeyWord("literary works", "wrong");
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book is not available in the bookstore", ex.getErrorMsg());
        }
    }


    @Test
    void getNumberOfBooksSoldPerCategoryAndKeywordSuccessfully() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);

        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(bookEntityList);
        Result<Integer> result = bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword("literary works", "1");
        Integer numberOfBooksSoldPerCategoryAndKeyword = (Integer) result.getData();
        Assertions.assertNotNull(numberOfBooksSoldPerCategoryAndKeyword);
    }


    @Test
    void getNumberOfBooksSoldPerCategoryAndKeywordByNoSuchBook() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findByCategoryAndKeyword(any(), any(), any())).thenReturn(new ArrayList<>());
        try {
            bookstoreService.getNumberOfBooksSoldPerCategoryAndKeyword("wrong", "Jane");
            Assert.fail("Expected a NoSuchBookException to be thrown");
        } catch (BookNotFoundException ex) {
            Assert.assertEquals("This book is not available in the bookstore", ex.getErrorMsg());
        }
    }


}