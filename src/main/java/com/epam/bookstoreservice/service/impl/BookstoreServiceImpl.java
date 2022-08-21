package com.epam.bookstoreservice.service.impl;

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
import com.epam.bookstoreservice.service.BookstoreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static final String SUCCESSFUL_MESSAGE = "successful";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Result<BookResponseDTO> addNewBook(BookRequestDTO book) {
        BookEntity bookEntity = bookRequestDtoToBookEntity.requestDtoToEntity(book);
        BookEntity newBookEntity = bookDao.save(bookEntity);
        return Result.success(SUCCESSFUL_MESSAGE, bookRequestDtoToBookEntity.entityToResponseDto(newBookEntity));
    }

    @Override
    public Result<BookResponseDTO> addBook(BookRequestDTO book) {
        BookEntity bookEntity = bookDao.findByTitle(book.getTitle());

        Integer totalCount = book.getTotalCount() + bookEntity.getTotalCount();
        bookEntity.setTotalCount(totalCount);
        BookEntity result = bookDao.save(bookEntity);

        return Result.success(SUCCESSFUL_MESSAGE, bookRequestDtoToBookEntity.entityToResponseDto(result));
    }

    @Override
    public Result<BookResponseDTO> getBookById(Integer id) {
        Optional<BookEntity> bookById = bookDao.findById(id);

        if (!bookById.isPresent()) {
            throw new BookNotFoundException();
        }

        BookEntity entity = bookById.get();

        return Result.success(SUCCESSFUL_MESSAGE, bookRequestDtoToBookEntity.entityToResponseDto(entity));
    }

    @Override
    public Result<List<BookResponseDTO>> getAllBooks() {
        List<BookEntity> allBooks = bookDao.findAll();
        ArrayList<BookResponseDTO> bookResponseDTOS = new ArrayList<>();

        allBooks.stream().forEach(bookEntity -> {
            BookResponseDTO bookResponseDto = bookRequestDtoToBookEntity.entityToResponseDto(bookEntity);
            bookResponseDTOS.add(bookResponseDto);
        });

        return Result.success(SUCCESSFUL_MESSAGE, bookResponseDTOS);
    }

    @Override
    public Result<Integer> getNumberOfBooksAvailableById(Integer id) {
        Optional<BookEntity> bookById = bookDao.findById(id);

        if (!bookById.isPresent()) {
            throw new BookNotFoundException();
        }

        BookEntity bookEntity = bookById.get();

        return Result.success(SUCCESSFUL_MESSAGE, bookEntity.getTotalCount() - bookEntity.getSold());
    }

    @Override
    @Transactional
    public Result<BookResponseDTO> sellABook(Integer id) {

            Optional<BookEntity> bookById = bookDao.findById(id);

            if (!bookById.isPresent()) {
                throw new BookNotFoundException();
            }

            BookEntity bookEntity = bookById.get();
            if (bookEntity.getTotalCount() - bookEntity.getSold() - 1 < 0) {
                throw new InsufficientInventoryException();
            }

            bookEntity.setSold(bookEntity.getSold() + 1);
            bookEntity.setTotalCount(bookEntity.getTotalCount() - 1);
            return Result.success(SUCCESSFUL_MESSAGE, bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(bookEntity)));


    }

    @Override
    @Transactional
    public Result<List<BookResponseDTO>> sellListOfBooks(List<SellDTO> sellDTOList) {
        Map<Integer, Integer> sellDtoMap = new HashMap<>();
        sellDTOList.stream().forEach(sellDTO ->
                sellDtoMap.put(sellDTO.getId(), sellDTO.getNumber()));


        List<Integer> idList = sellDTOList.stream().map(SellDTO::getId).collect(Collectors.toList());
        List<BookEntity> books = bookDao.findAllById(idList);

        ArrayList<BookResponseDTO> bookResponseDTOS = new ArrayList<>();

        for (BookEntity book : books) {
            if (book.getTotalCount() - book.getSold() - sellDtoMap.get(book.getId()) < 0) {
                throw new InsufficientInventoryException();
            }
        }

        for (BookEntity book : books) {
            book.setSold(book.getSold() + sellDtoMap.get(book.getId()));
            book.setTotalCount(book.getTotalCount() - sellDtoMap.get(book.getId()));
            bookResponseDTOS.add(bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(book)));
        }

        return Result.success(SUCCESSFUL_MESSAGE, bookResponseDTOS);

    }

    @Override
    public Result<BookResponseDTO> updateABook(Integer id, BookRequestDTO book) {
        if (Objects.nonNull(book.getId()) && !Objects.equals(id, book.getId())) {
            throw new UnmatchedIdException();
        }
        Optional<BookEntity> bookById = bookDao.findById(id);
        if (!bookById.isPresent()) {
            throw new BookNotFoundException();
        }
        BookEntity bookEntity = bookById.get();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setCategory(book.getCategory());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setTotalCount(book.getTotalCount());
        return Result.success(SUCCESSFUL_MESSAGE, bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(bookEntity)));
    }

    @Override
    public Result<List<BookResponseDTO>> getBooksByCategoryAndKeyWord(String category, String keyword) {
        return Result.success(SUCCESSFUL_MESSAGE, getBooksByCategoryAndKeyWordUtil(category, keyword));

    }

    @Override
    public Result<Integer> getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {
        List<BookResponseDTO> booksByCategoryAndKeyWord = getBooksByCategoryAndKeyWordUtil(category, keyword);
        int totalSold = 0;
        for (BookResponseDTO book : booksByCategoryAndKeyWord) {
            totalSold += book.getSold();
        }
        return Result.success(SUCCESSFUL_MESSAGE, totalSold);
    }


    private List<BookResponseDTO> getBooksByCategoryAndKeyWordUtil(String category, String keyword) {
        Integer bookId = -1;
        try {

            bookId = Integer.valueOf(keyword);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        List<BookEntity> byCategoryAndKeyword = bookDao.findByCategoryAndKeyword(category, bookId, keyword);

        if (byCategoryAndKeyword.isEmpty()) {
            throw new BookNotFoundException();
        }
        List<BookResponseDTO> bookResponseDTOListByAuthorOrTitle = new ArrayList<>();
        byCategoryAndKeyword.forEach(bookEntity ->
                bookResponseDTOListByAuthorOrTitle.add(bookRequestDtoToBookEntity.entityToResponseDto(bookEntity))
        );
        return bookResponseDTOListByAuthorOrTitle;
    }
}

