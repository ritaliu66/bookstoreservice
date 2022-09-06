package com.epam.bookstoreservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDTO {

    private Integer id;

    private String author;

    private String title;

    private String category;

    private BigDecimal price;

    private Integer totalCount;

}
