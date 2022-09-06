package com.epam.bookstoreservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * the book entity
 */
@Table(name = "tb_book")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String author;

    @Column
    private String title;

    @Column
    private String category;

    @Column
    private BigDecimal price;

    @Column
    private Integer totalCount;

    @Column
    private Integer sold=0;

}
