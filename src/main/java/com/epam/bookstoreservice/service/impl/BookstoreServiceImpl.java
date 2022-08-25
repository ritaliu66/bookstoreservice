package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dao.BookDao;
import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.entity.BookEntity;
import com.epam.bookstoreservice.exception.InsufficientInventoryException;
import com.epam.bookstoreservice.exception.BookNotFoundException;
import com.epam.bookstoreservice.exception.UnmatchedIdException;
import com.epam.bookstoreservice.mapper.BookDtoAndBookEntityMapper;
import com.epam.bookstoreservice.service.BookstoreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {

    private final BookDtoAndBookEntityMapper bookDtoAndBookEntityMapper;

    private final BookDao bookDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Integer SELL_A_BOOK_NUMBER = 1;


    @Override
    public BookResponseDTO addNewBook(BookRequestDTO bookRequestDTO) {
        return bookDtoAndBookEntityMapper
                .entityToResponseDto(bookDao.save(bookDtoAndBookEntityMapper.requestDtoToEntity(bookRequestDTO)));
    }

    @Override
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {
        Optional<BookEntity> bookFindById = bookDao.findById(bookRequestDTO.getId());
        BookEntity bookEntityFindByTitle = bookFindById.orElseThrow(BookNotFoundException::new);

        Integer totalCount = bookRequestDTO.getTotalCount() + bookEntityFindByTitle.getTotalCount();
        bookEntityFindByTitle.setTotalCount(totalCount);

        return bookDtoAndBookEntityMapper.entityToResponseDto(bookDao.save(bookEntityFindByTitle));
    }

    @Override
    public BookResponseDTO getBookById(Integer id) {
        Optional<BookEntity> bookEntityOptionalFindById = bookDao.findById(id);

        return bookDtoAndBookEntityMapper
                .entityToResponseDto(bookEntityOptionalFindById.orElseThrow(BookNotFoundException::new));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        List<BookEntity> allBookEntityList = bookDao.findAll();

        return allBookEntityList
                .stream()
                .map(bookDtoAndBookEntityMapper::entityToResponseDto).collect(Collectors.toList());
    }

    @Override
    public Integer getNumberOfBooksAvailableById(Integer id) {
        Optional<BookEntity> bookEntityOptionalFindById = bookDao.findById(id);

        BookEntity bookEntityFindById = bookEntityOptionalFindById.orElseThrow(BookNotFoundException::new);

        return bookEntityFindById.getTotalCount() - bookEntityFindById.getSold();
    }

    @Override
    @Transactional
    public BookResponseDTO sellABook(Integer id) {

        return sellBook(id, SELL_A_BOOK_NUMBER);
    }

    @Override
    @Transactional
    public List<BookResponseDTO> sellListOfBooks(List<SellDTO> sellDTOList) {

        return sellDTOList
                .stream()
                .map(sellDTO -> sellBook(sellDTO.getId(), sellDTO.getSellNumber())).collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateABook(Integer id, BookRequestDTO bookRequestDTO) {
        if (Objects.nonNull(bookRequestDTO.getId()) && !Objects.equals(id, bookRequestDTO.getId())) {
            throw new UnmatchedIdException();
        }

        Optional<BookEntity> bookById = bookDao.findById(id);

        if (bookById.isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookDtoAndBookEntityMapper
                .entityToResponseDto(bookDao.save(bookDtoAndBookEntityMapper.requestDtoToEntity(bookRequestDTO)));
    }

    @Override
    public List<BookResponseDTO> getBooksByCategoryAndKeyWord(String category, String keyword) {
        return getBooksByCategoryAndKeyWordUtil(category, keyword);

    }

    @Override
    public Integer getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {
        List<BookResponseDTO> bookEntityListFindByCategoryAndKeyWord
                = getBooksByCategoryAndKeyWordUtil(category, keyword);

        return bookEntityListFindByCategoryAndKeyWord
                .stream().mapToInt(BookResponseDTO::getSold).sum();
    }


    private List<BookResponseDTO> getBooksByCategoryAndKeyWordUtil(String category, String keyword) {
        int bookId = -1;
        try {
            bookId = Integer.parseInt(keyword);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        List<BookEntity> bookEntityListFindByCategoryAndKeyword = bookDao.findByCategoryAndKeyword(category, bookId, keyword);

        if (bookEntityListFindByCategoryAndKeyword.isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookEntityListFindByCategoryAndKeyword
                .stream()
                .map(bookDtoAndBookEntityMapper::entityToResponseDto).collect(Collectors.toList());
    }

    private BookResponseDTO sellBook(Integer id, Integer sellNumber) {
        Optional<BookEntity> bookFindById = bookDao.findById(id);

        BookEntity bookEntityFindById = bookFindById.orElseThrow(BookNotFoundException::new);

        if (bookEntityFindById.getTotalCount() - sellNumber < 0) {
            throw new InsufficientInventoryException();
        }

        bookEntityFindById.setSold(bookEntityFindById.getSold() + sellNumber);
        bookEntityFindById.setTotalCount(bookEntityFindById.getTotalCount() - sellNumber);

        return bookDtoAndBookEntityMapper.entityToResponseDto(bookDao.save(bookEntityFindById));
    }

}

