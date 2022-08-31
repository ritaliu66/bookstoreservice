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

    private static final Integer SALES_NUMBER_OF_A_BOOK = 1;

    private static final Integer DEFAULT_BOOK_ID=-1;


    @Override
    public BookResponseDTO addNewBook(BookRequestDTO bookRequestDTO) {
        return bookDtoAndBookEntityMapper
                .entityToResponseDto(bookDao.save(bookDtoAndBookEntityMapper.requestDtoToEntity(bookRequestDTO)));
    }

    @Override
    public BookResponseDTO addExistentBook(BookRequestDTO bookRequestDTO) {

        BookEntity bookEntityFindById = checkAndGetBookEntityFindByIdIfExist(bookRequestDTO.getId());

        addBookEntityTotalCount(bookRequestDTO, bookEntityFindById);

        return bookDtoAndBookEntityMapper.entityToResponseDto(bookDao.save(bookEntityFindById));
    }


    @Override
    public BookResponseDTO getBookById(Integer id) {

        return bookDtoAndBookEntityMapper
                .entityToResponseDto(checkAndGetBookEntityFindByIdIfExist(id));
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

        return checkAndGetBookEntityFindByIdIfExist(id).getTotalCount();
    }

    @Override
    @Transactional
    public BookResponseDTO sellABook(Integer id) {

        checkInventory(id, SALES_NUMBER_OF_A_BOOK);

        return sellBookByIdAndSalesNumber(id, SALES_NUMBER_OF_A_BOOK);
    }

    @Override
    @Transactional
    public List<BookResponseDTO> sellListOfBooks(List<SellDTO> sellDTOList) {

        sellDTOList.forEach(sellDTO -> checkInventory(sellDTO.getId(), sellDTO.getSalesNumber()));

        return sellDTOList
                .stream()
                .map(sellDTO -> sellBookByIdAndSalesNumber(sellDTO.getId(), sellDTO.getSalesNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateABook(Integer id, BookRequestDTO bookRequestDTO) {

        validId(id, bookRequestDTO);

        checkAndGetBookEntityFindByIdIfExist(id);

        return bookDtoAndBookEntityMapper
                .entityToResponseDto(bookDao.save(bookDtoAndBookEntityMapper.requestDtoToEntity(bookRequestDTO)));
    }


    @Override
    public List<BookResponseDTO> getBooksByCategoryAndKeyWord(String category, String keyword) {
        return getBooksByCategoryAndKeyWordUtil(category, keyword);

    }

    @Override
    public Integer getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {

        return getBooksByCategoryAndKeyWordUtil(category, keyword)
                .stream().mapToInt(BookResponseDTO::getSold).sum();
    }

    private void addBookEntityTotalCount(BookRequestDTO bookRequestDTO, BookEntity bookEntityFindById) {

        Integer totalCount = bookRequestDTO.getTotalCount() + bookEntityFindById.getTotalCount();

        bookEntityFindById.setTotalCount(totalCount);
    }

    private BookEntity checkAndGetBookEntityFindByIdIfExist(Integer id) {
        Optional<BookEntity> bookFindById = bookDao.findById(id);

        return bookFindById.orElseThrow(BookNotFoundException::new);
    }

    private void checkInventory(Integer id, Integer salesNumber) {
        BookEntity bookEntityFindById = checkAndGetBookEntityFindByIdIfExist(id);

        int remainingInventory = bookEntityFindById.getTotalCount() - salesNumber;

        if (remainingInventory < 0) {
            throw new InsufficientInventoryException();
        }

    }

    private BookResponseDTO sellBookByIdAndSalesNumber(Integer id, Integer salesNumber) {

        BookEntity bookEntityFindById = checkAndGetBookEntityFindByIdIfExist(id);

        bookEntityFindById.setSold(bookEntityFindById.getSold() + salesNumber);
        bookEntityFindById.setTotalCount(bookEntityFindById.getTotalCount() - salesNumber);

        return bookDtoAndBookEntityMapper.entityToResponseDto(bookDao.save(bookEntityFindById));
    }

    private void validId(Integer id, BookRequestDTO bookRequestDTO) {

        boolean idNonNullAndUnmatched
                = Objects.nonNull(bookRequestDTO.getId()) && !Objects.equals(id, bookRequestDTO.getId());

        if (idNonNullAndUnmatched){
            throw new UnmatchedIdException();
        }
    }

    private List<BookResponseDTO> getBooksByCategoryAndKeyWordUtil(String category, String keyword) {

        List<BookEntity> bookEntityListFindByCategoryAndKeyword = bookDao
                .findByCategoryAndKeyword(category, getBookIdWhenKeywordIsNumber(keyword), keyword);

        bookEntityListFindByCategoryAndKeyword
                .forEach(bookEntity -> checkAndGetBookEntityFindByIdIfExist(bookEntity.getId()));

        return bookEntityListFindByCategoryAndKeyword
                .stream()
                .map(bookDtoAndBookEntityMapper::entityToResponseDto).collect(Collectors.toList());
    }


    private int getBookIdWhenKeywordIsNumber(String keyword) {
        int bookId = DEFAULT_BOOK_ID;
        try {
            bookId = Integer.parseInt(keyword);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return bookId;
    }
}

