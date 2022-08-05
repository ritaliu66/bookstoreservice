package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.BookstoreServiceApplication;
import com.epam.bookstoreservice.dao.BookDao;
import com.epam.bookstoreservice.dto.request.BookRequestDto;
import com.epam.bookstoreservice.dto.request.SellDto;
import com.epam.bookstoreservice.dto.response.BookResponseDto;
import com.epam.bookstoreservice.entity.BookEntity;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
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

    private static final BookRequestDto BOOK_REQUEST_DTO = new BookRequestDto(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100);

    private static final BookEntity EXISTENT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 600);

    private static final BookEntity NONEXISTENT_BOOK_ENTITY = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 100, 0);

    private static final BookEntity SOLD_OUT_BOOK = new BookEntity(1, "Jane", "Pride and Prejudice", "literary works", PRICE, 1000, 1000);

    private static final Integer ID=1;

    private static final List<BookEntity> BOOK_ENTITY_LIST=new ArrayList<>();

    private static final SellDto SELL_DTO=new SellDto(1,2);


    @BeforeEach
    public void init() {
        bookstoreService = new BookstoreServiceImpl(bookRequestDtoToBookEntity, bookDao);
    }


    @Test
    void addNewBook() {
        Mockito.when(bookDao.save(any()))
                .thenReturn(NONEXISTENT_BOOK_ENTITY);

        BookResponseDto result = bookstoreService.addNewBook(BOOK_REQUEST_DTO);

        Assertions.assertEquals(result.getAuthor(), "Jane");

    }

    @Test
    void addBook() {
        Mockito.when(bookDao.findByTitle(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.save(any()))
                .thenReturn(EXISTENT_BOOK_ENTITY);

        BookResponseDto result = bookstoreService.addBook(BOOK_REQUEST_DTO);

        Assertions.assertEquals(result.getTotalCount(), 1100);
    }

    @Test
    void getBookById(){
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        BookResponseDto bookById = bookstoreService.getBookById(ID);
        Assertions.assertNotNull(bookById);
    }

    @Test
    void getAllBooks(){
        BOOK_ENTITY_LIST.add(EXISTENT_BOOK_ENTITY);
        Mockito.when(bookDao.findAll()).thenReturn(BOOK_ENTITY_LIST);
        List<BookResponseDto> allBooks = bookstoreService.getAllBooks();
        Assertions.assertTrue(!allBooks.isEmpty());

    }

    @Test
    void getNumberOfBooksAvailableById(){
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Integer numberOfBooksAvailableById = bookstoreService.getNumberOfBooksAvailableById(ID);
        Assertions.assertEquals(numberOfBooksAvailableById,400);
    }

    @Test
    void sellABookSuccessfully(){
        Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(EXISTENT_BOOK_ENTITY));
        Boolean result = bookstoreService.sellABook(ID);
        Assertions.assertEquals(result,true);
    }

    @Test
    void sellABookUnsuccessfully(){
        try {
            Mockito.when(bookDao.findById(any())).thenReturn(Optional.of(SOLD_OUT_BOOK));
            Boolean result = bookstoreService.sellABook(ID);
            Assert.fail("Expected an InsufficientInventoryException to be thrown");
        } catch (InsufficientInventoryException ex) {
            Assert.assertEquals(ex.getErrorMsg(), "This book is out of stock");
        }

    }
    @Test
    void sellListOfBooksSuccessfully(){
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(EXISTENT_BOOK_ENTITY);

        List<SellDto> sellDtoList = new ArrayList<>();
        sellDtoList.add(SELL_DTO);

        Mockito.when(bookDao.findAllById(any())).thenReturn(bookEntityList);
        Boolean result = bookstoreService.sellListOfBooks(sellDtoList);
        Assertions.assertEquals(result,true);
        Assertions.assertEquals(EXISTENT_BOOK_ENTITY.getSold(),598);
    }


}