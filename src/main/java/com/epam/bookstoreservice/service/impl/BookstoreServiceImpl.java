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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {

    private final BookDtoAndBookEntityMapper bookRequestDtoToBookEntity;

    private final BookDao bookDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public BookResponseDTO addNewBook(BookRequestDTO bookRequestDTO) {
        return bookRequestDtoToBookEntity
                .entityToResponseDto(bookDao.save(bookRequestDtoToBookEntity.requestDtoToEntity(bookRequestDTO)));
    }

    @Override
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {
        BookEntity bookEntityFindByTitle = bookDao.findByTitle(bookRequestDTO.getTitle());

        Integer totalCount = bookRequestDTO.getTotalCount() + bookEntityFindByTitle.getTotalCount();
        bookEntityFindByTitle.setTotalCount(totalCount);

        return bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(bookEntityFindByTitle));
    }

    @Override
    public BookResponseDTO getBookById(Integer id) {
        Optional<BookEntity> bookEntityOptionalFindById = bookDao.findById(id);

        return bookRequestDtoToBookEntity
                .entityToResponseDto(bookEntityOptionalFindById.orElseThrow(BookNotFoundException::new));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        List<BookEntity> allBookEntityList = bookDao.findAll();

        return allBookEntityList
                .stream()
                .map(bookRequestDtoToBookEntity::entityToResponseDto).collect(Collectors.toList());
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

        Optional<BookEntity> bookEntityOptionalFindById = bookDao.findById(id);

        BookEntity bookEntityFindById = bookEntityOptionalFindById.orElseThrow(BookNotFoundException::new);

        if (bookEntityFindById.getTotalCount() - bookEntityFindById.getSold() - 1 < 0) {
            throw new InsufficientInventoryException();
        }

        bookEntityFindById.setSold(bookEntityFindById.getSold() + 1);
        bookEntityFindById.setTotalCount(bookEntityFindById.getTotalCount() - 1);

        return bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(bookEntityFindById));


    }

    @Override
    @Transactional
    public List<BookResponseDTO> sellListOfBooks(List<SellDTO> sellDTOList) {

        Map<Integer, Integer> sellDtoMap = sellDTOList.stream().collect(Collectors.toMap(SellDTO::getId, SellDTO::getSellNumber));

        List<BookEntity> bookEntityListFindById = bookDao
                .findAllById(sellDTOList.stream().map(SellDTO::getId).collect(Collectors.toList()));

        for (BookEntity book : bookEntityListFindById) {
            if (book.getTotalCount() - book.getSold() - sellDtoMap.get(book.getId()) < 0) {
                throw new InsufficientInventoryException();
            }
        }

        return bookEntityListFindById.stream()
                .map(bookEntity -> {
                    bookEntity.setSold(bookEntity.getSold() + sellDtoMap.get(bookEntity.getId()));
                    bookEntity.setTotalCount(bookEntity.getTotalCount() - sellDtoMap.get(bookEntity.getId()));
                    return bookRequestDtoToBookEntity.entityToResponseDto(bookDao.save(bookEntity));
                }).collect(Collectors.toList());

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

        return bookRequestDtoToBookEntity
                .entityToResponseDto(bookDao.save(bookRequestDtoToBookEntity.requestDtoToEntity(bookRequestDTO)));
    }

    @Override
    public List<BookResponseDTO> getBooksByCategoryAndKeyWord(String category, String keyword) {
        return getBooksByCategoryAndKeyWordUtil(category, keyword);

    }

    @Override
    public Integer getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword) {
        List<BookResponseDTO> bookEntityListFindByCategoryAndKeyWord = getBooksByCategoryAndKeyWordUtil(category, keyword);

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
                .map(bookRequestDtoToBookEntity::entityToResponseDto).collect(Collectors.toList());
    }
}

