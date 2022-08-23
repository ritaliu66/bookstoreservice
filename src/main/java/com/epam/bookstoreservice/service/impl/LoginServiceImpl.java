package com.epam.bookstoreservice.service.impl;

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

import java.util.HashMap;
import java.util.Map;
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
    public String loginAndReturnToken(UserRequestDTO userRequestDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDto.getPhoneNumber());
        if (Objects.isNull(userDetails) ||
                !passwordEncoder.matches(userRequestDto.getPassword(), userDetails.getPassword())) {
            throw new WrongPhoneNumberOrPasswordException();
        }

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtil.generateToken((UserEntity) userDetails);

        Map<String, String> tokenInfoMap = new HashMap<>();
        tokenInfoMap.put("token", token);
        tokenInfoMap.put("tokenHead", TOKEN_HEADER);

        return tokenInfoMap.toString();
    }
}
