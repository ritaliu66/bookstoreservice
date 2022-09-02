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

    private static final Integer SALES_NUMBER_OF_A_BOOK = 1;

    private static final Integer DEFAULT_BOOK_ID = -1;


    @Override
    public BookResponseDTO addNewBook(BookRequestDTO bookRequestDTO) {
        return bookDtoAndBookEntityMapper
                .entityToResponseDto(bookDao.save(bookDtoAndBookEntityMapper.requestDtoToEntity(bookRequestDTO)));
    }

    @Override
    public BookResponseDTO addExistentBook(BookRequestDTO bookRequestDTO) {

        BookEntity bookEntityFindById = checkAndGetABookEntityFindByIdIfExist(bookRequestDTO.getId());

        addBookEntityTotalCount(bookRequestDTO, bookEntityFindById);

        return bookDtoAndBookEntityMapper.entityToResponseDto(bookDao.save(bookEntityFindById));
    }


    @Override
    public BookResponseDTO getBookById(Integer id) {

        return bookDtoAndBookEntityMapper
                .entityToResponseDto(checkAndGetABookEntityFindByIdIfExist(id));
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

        return checkAndGetABookEntityFindByIdIfExist(id).getTotalCount();
    }

    @Override
    @Transactional
    public BookResponseDTO sellABook(Integer id) {

        checkInventoryOfABook(checkAndGetABookEntityFindByIdIfExist(id), SALES_NUMBER_OF_A_BOOK);

        return sellBookByIdAndSalesNumber(checkAndGetABookEntityFindByIdIfExist(id), SALES_NUMBER_OF_A_BOOK);
    }

    @Override
    @Transactional
    public List<BookResponseDTO> sellListOfBooks(List<SellDTO> sellDTOList) {

        checkInventoryOfBooks(sellDTOList);

        return sellDTOList
                .stream()
                .map(sellDTO ->
                        sellBookByIdAndSalesNumber(checkAndGetABookEntityFindByIdIfExist(sellDTO.getId())
                                , sellDTO.getSalesNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateABook(Integer id, BookRequestDTO bookRequestDTO) {

        validId(id, bookRequestDTO);

        checkAndGetABookEntityFindByIdIfExist(id);

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

    private BookEntity checkAndGetABookEntityFindByIdIfExist(Integer id) {
        Optional<BookEntity> bookFindById = bookDao.findById(id);

        return bookFindById.orElseThrow(BookNotFoundException::new);
    }

    private void checkInventoryOfABook(BookEntity bookEntity, Integer salesNumber) {

        int remainingInventory = bookEntity.getTotalCount() - salesNumber;

        if (remainingInventory < 0) {
            throw new InsufficientInventoryException();
        }

    }

    private void checkInventoryOfBooks(List<SellDTO> sellDTOList) {
        sellDTOList.forEach(sellDTO ->
                checkInventoryOfABook(checkAndGetABookEntityFindByIdIfExist(sellDTO.getId()), sellDTO.getSalesNumber()));
    }

    private BookResponseDTO sellBookByIdAndSalesNumber(BookEntity bookEntity, Integer salesNumber) {

        bookEntity.setSold(bookEntity.getSold() + salesNumber);
        bookEntity.setTotalCount(bookEntity.getTotalCount() - salesNumber);

        return bookDtoAndBookEntityMapper.entityToResponseDto(bookDao.save(bookEntity));
    }

    private void validId(Integer id, BookRequestDTO bookRequestDTO) {

        boolean idNonNullAndUnmatched
                = Objects.nonNull(bookRequestDTO.getId()) && !Objects.equals(id, bookRequestDTO.getId());

        if (idNonNullAndUnmatched) {
            throw new UnmatchedIdException();
        }
    }

    private List<BookResponseDTO> getBooksByCategoryAndKeyWordUtil(String category, String keyword) {

        List<BookEntity> bookEntityListFindByCategoryAndKeyword = bookDao
                .findByCategoryAndKeyword(category, getBookIdWhenKeywordIsNumber(keyword), keyword);

        checkBookEntityListIfExist(bookEntityListFindByCategoryAndKeyword);

        return bookEntityListFindByCategoryAndKeyword
                .stream()
                .map(bookDtoAndBookEntityMapper::entityToResponseDto).collect(Collectors.toList());
    }

    private void checkBookEntityListIfExist(List<BookEntity> bookEntityListFindByCategoryAndKeyword) {
        if (bookEntityListFindByCategoryAndKeyword.isEmpty()) {
            throw new BookNotFoundException();
        }
    }

    private int getBookIdWhenKeywordIsNumber(String keyword) {
        if (checkKeyWordIsDigit(keyword)){
            return Integer.parseInt(keyword);
        }
        return DEFAULT_BOOK_ID;
    }

    private boolean checkKeyWordIsDigit(String keyword) {
        char[] keywordChars = keyword.toCharArray();

        for (char keywordChar : keywordChars) {
            boolean keywordCharIsNotDigit = !Character.isDigit(keywordChar);

            if (keywordCharIsNotDigit) {
                return false;
            }
        }
        return true;
    }


}

