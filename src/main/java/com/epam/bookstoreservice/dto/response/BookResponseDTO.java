package com.epam.bookstoreservice.dto.response;

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
public class BookResponseDTO {
    private Integer id;

    private String author;

    private String title;

    private String category;

    private BigDecimal price;

    private Integer totalCount;

    private Integer sold;
}
