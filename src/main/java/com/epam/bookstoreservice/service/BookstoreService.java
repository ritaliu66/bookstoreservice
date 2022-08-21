package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.BookRequestDTO;
import com.epam.bookstoreservice.dto.request.SellDTO;
import com.epam.bookstoreservice.dto.response.BookResponseDTO;
import com.epam.bookstoreservice.dto.response.Result;

import java.util.List;

/**
 * the service for bookstore
 */
public interface BookstoreService {
    Result<BookResponseDTO> addNewBook(BookRequestDTO book);

    Result<BookResponseDTO> addBook(BookRequestDTO book);

    Result<BookResponseDTO> getBookById(Integer id);

    Result<List<BookResponseDTO>> getAllBooks();

    Result<Integer> getNumberOfBooksAvailableById(Integer id);

    Result<BookResponseDTO> sellABook(Integer id);

    Result<List<BookResponseDTO>> sellListOfBooks(List<SellDTO> sellDTOList);

    Result<BookResponseDTO> updateABook(Integer id, BookRequestDTO book);

    Result<List<BookResponseDTO>> getBooksByCategoryAndKeyWord(String category, String keyword);

    Result<Integer> getNumberOfBooksSoldPerCategoryAndKeyword(String category, String keyword);
}
