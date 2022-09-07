package com.epam.bookstoreservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

/**
 * the model for Integer
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class IntegerModel extends RepresentationModel<IntegerModel> {

    private final Integer content;

    public IntegerModel(Integer number){
        this.content=number;
    }
}
