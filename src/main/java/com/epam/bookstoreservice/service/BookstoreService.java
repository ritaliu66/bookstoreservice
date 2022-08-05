package com.epam.bookstoreservice.service;

import com.epam.bookstoreservice.dto.request.BookRequestDto;
import com.epam.bookstoreservice.dto.request.SellDto;
import com.epam.bookstoreservice.dto.response.BookResponseDto;
import com.epam.bookstoreservice.entity.BookEntity;

import java.util.List;

/**
 * the service for bookstore
 */
public interface BookstoreService {
    BookResponseDto addNewBook(BookRequestDto book);

    BookResponseDto addBook(BookRequestDto book);

    BookResponseDto getBookById(Integer id);

    List<BookResponseDto> getAllBooks();

    Integer getNumberOfBooksAvailableById(Integer id);

    Boolean sellABook(Integer id);

    Boolean sellListOfBooks(List<SellDto> sellDtoList);
}
