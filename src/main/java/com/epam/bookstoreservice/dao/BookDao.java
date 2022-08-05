package com.epam.bookstoreservice.dao;

import com.epam.bookstoreservice.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<BookEntity,Integer> {


    BookEntity findByTitle(String title);
}
