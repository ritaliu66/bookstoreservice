package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;

import java.util.List;

/**
 * the service for bookstore
 */
public interface BookstoreService {
    BookResponseDTO addNewBook(BookRequestDTO book);

    BookResponseDTO addBook(BookRequestDTO book);

    BookResponseDTO getBookById(Integer id);

    List<BookResponseDTO> getAllBooks();

    Integer getNumberOfBooksAvailableById(Integer id);

    BookResponseDTO sellABook(Integer id);

    List<BookResponseDTO> sellListOfBooks(List<SellDTO> sellDTOList);

    BookResponseDTO updateABook(Integer id, BookRequestDTO book);

    List<BookResponseDTO> getBooksByCategoryAndKeyWord(String category, String keyword);

    Integer getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword);
}
