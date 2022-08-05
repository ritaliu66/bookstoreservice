package com.epam.bookstoreservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter
@Setter
public class LoginUser  {

    private Integer id;

    private String userName;

    private String password;

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1");
        System.out.println(encode);
    }

}
