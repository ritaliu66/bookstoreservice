package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.dto.response.TokenResponseDTO;
import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.exception.WrongPhoneNumberOrPasswordException;
import com.epam.bookstoreservice.security.jwt.JwtTokenUtil;
import com.epam.bookstoreservice.security.userdetailsservice.UserDetailsServiceImpl;
import com.epam.bookstoreservice.dto.request.UserRequestDTO;
import com.epam.bookstoreservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Objects;

/**
 * the service for user login
 */
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private static final String TOKEN_HEADER = "Bearer";

    @Override
    public TokenResponseDTO loginAndReturnToken(UserRequestDTO userRequestDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDto.getPhoneNumber());

        validatePhoneNumberAndPassword(userRequestDto, userDetails);

        setAuthentication(userDetails);

        return new TokenResponseDTO(TOKEN_HEADER,jwtTokenUtil.generateToken((UserEntity) userDetails));
    }

    private void validatePhoneNumberAndPassword(UserRequestDTO userRequestDto, UserDetails userDetails) {
        boolean userDetailIsNull = Objects.isNull(userDetails);
        boolean passwordNotMatch = !passwordEncoder
                .matches(Objects.requireNonNull(userRequestDto).getPassword()
                        , Objects.requireNonNull(userRequestDto).getPassword());

        if (userDetailIsNull||passwordNotMatch) {
            throw new WrongPhoneNumberOrPasswordException();
        }
    }

    private void setAuthentication(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
