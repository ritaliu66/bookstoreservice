package com.epam.bookstoreservice.dao;

import com.epam.bookstoreservice.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * dao for tb_Book
 */
public interface BookDao extends JpaRepository<BookEntity,Integer> {


    @Query("select b from BookEntity b where b.category = :category " +
            "and (b.id = :bookId or b.author = :keyword or b.title = :keyword)")
    List<BookEntity> findByCategoryAndKeyword(String category,Integer bookId,String keyword);
}
