package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dao.BookDao;
import com.epam.bookstoreservice.dto.request.BookRequestDto;
import com.epam.bookstoreservice.dto.request.SellDto;
import com.epam.bookstoreservice.dto.response.BookResponseDto;
import com.epam.bookstoreservice.entity.BookEntity;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.NoSuchBookException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import com.epam.bookstoreservice.mapper.BookDtoToBookEntityMapper;
import com.epam.bookstoreservice.service.BookstoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {

    private final BookDtoToBookEntityMapper bookRequestDtoToBookEntity;

    private final BookDao bookDao;


    @Override
    public BookResponseDto addNewBook(BookRequestDto book) {
        BookEntity bookEntity = bookRequestDtoToBookEntity.requestDtoToEntity(book);
        BookEntity newBookEntity = bookDao.save(bookEntity);
        return bookRequestDtoToBookEntity.entityToResponseDto(newBookEntity);
    }

    @Override
    public BookResponseDto addBook(BookRequestDto book) {
        BookEntity bookEntity = bookDao.findByTitle(book.getTitle());

        Integer totalCount = book.getTotalCount() + bookEntity.getTotalCount();
        bookEntity.setTotalCount(totalCount);
        BookEntity result = bookDao.save(bookEntity);

        return bookRequestDtoToBookEntity.entityToResponseDto(result);
    }

    @Override
    public BookResponseDto getBookById(Integer id) {
        Optional<BookEntity> bookById = bookDao.findById(id);
        BookEntity entity = bookById.get();

        return bookRequestDtoToBookEntity.entityToResponseDto(entity);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<BookEntity> allBooks = bookDao.findAll();
        ArrayList<BookResponseDto> bookResponseDtos = new ArrayList<>();

        allBooks.stream().forEach(bookEntity -> {
            BookResponseDto bookResponseDto = bookRequestDtoToBookEntity.entityToResponseDto(bookEntity);
            bookResponseDtos.add(bookResponseDto);
        });

        return bookResponseDtos;
    }

    @Override
    public Integer getNumberOfBooksAvailableById(Integer id) {
        Optional<BookEntity> bookById = bookDao.findById(id);
        BookEntity bookEntity = bookById.get();
        return bookEntity.getTotalCount() - bookEntity.getSold();
    }

    @Override
    public Boolean sellABook(Integer id) {
        Optional<BookEntity> bookById = bookDao.findById(id);
        BookEntity bookEntity = bookById.get();
        if (bookEntity.getTotalCount() - bookEntity.getSold() - 1 < 0) {
            throw new InsufficientInventoryException();
        }

        bookEntity.setSold(bookEntity.getSold() + 1);
        bookDao.save(bookEntity);
        return true;
    }

    @Override
    public Boolean sellListOfBooks(List<SellDto> sellDtoList) {
        Map<Integer, Integer> sellDtoMap = new HashMap<>();

        sellDtoList.stream().forEach(sellDto -> {
            sellDtoMap.put(sellDto.getId(), sellDto.getNumber());
        });

        List<Integer> idList = sellDtoList.stream().map(SellDto::getId).collect(Collectors.toList());
        List<BookEntity> books = bookDao.findAllById(idList);

        for (BookEntity book : books) {
            if (book.getTotalCount() - book.getSold() - sellDtoMap.get(book.getId()) < 0) {
                throw new InsufficientInventoryException();
            }
            book.setSold(book.getSold() + sellDtoMap.get(book.getId()));
            bookDao.save(book);
        }

        return true;

    }

    @Override
    public BookResponseDto updateABook(Integer id, BookRequestDto book) {
        if (Objects.nonNull(book.getId()) && !Objects.equals(id, book.getId())) {
            throw new UnmatchedIdException();
        }

        Optional<BookEntity> bookById = bookDao.findById(id);
        if (bookById.isEmpty()) {
            throw new NoSuchBookException();
        }
        BookEntity bookEntity = bookById.get();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setCategory(book.getCategory());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setTotalCount(book.getTotalCount());
        return bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(bookEntity));
    }

    @Override
    public List<BookResponseDto> getBooksByCategoryAndKeyWord(String category, String keyword) {
        return getBooksByCategoryAndKeyWordUtil(category, keyword);

    }

    @Override
    public Integer getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {
        List<BookResponseDto> booksByCategoryAndKeyWord = getBooksByCategoryAndKeyWordUtil(category, keyword);
        int totalSold = 0;
        for (BookResponseDto book : booksByCategoryAndKeyWord) {
            totalSold += book.getSold();
        }
        return totalSold;
    }


    private List<BookResponseDto> getBooksByCategoryAndKeyWordUtil(String category, String keyword) {
        Integer bookId = -1;
        try {

            bookId = Integer.valueOf(keyword);

        } catch (Exception ignored) {
            
        }
        List<BookEntity> byCategoryAndKeyword = bookDao.findByCategoryAndKeyword(category, bookId, keyword);

        if (byCategoryAndKeyword.isEmpty()) {
            throw new NoSuchBookException();
        }
        List<BookResponseDto> bookResponseDtoListByAuthorOrTitle = new ArrayList<>();
        byCategoryAndKeyword.forEach(bookEntity ->
                bookResponseDtoListByAuthorOrTitle.add(bookRequestDtoToBookEntity.entityToResponseDto(bookEntity))
        );
        return bookResponseDtoListByAuthorOrTitle;
    }
}

