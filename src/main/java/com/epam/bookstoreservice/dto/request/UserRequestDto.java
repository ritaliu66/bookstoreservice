package com.epam.bookstoreservice.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class UserRequestDto {

    private String username;

    private String password;

    private Integer phoneNumber;
}
