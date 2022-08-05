package com.epam.bookstoreservice.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class BookRequestDto {

    private Integer id;

    private String author;

    private String title;

    private String category;

    private BigDecimal price;

    private Integer totalCount;

}
