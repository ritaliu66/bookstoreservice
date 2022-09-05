package com.epam.bookstoreservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponseDTO {

    private String tokenHead;

    private String token;
}
