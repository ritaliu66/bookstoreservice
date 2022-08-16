package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.config.jwt.JwtTokenUtil;
import com.epam.bookstoreservice.config.security.UserDetailsServiceImpl;
import com.epam.bookstoreservice.dto.request.UserRequestDto;
import com.epam.bookstoreservice.model.LoginUserDetails;
import com.epam.bookstoreservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final String tokenHeader = "Bearer";

    @Override
    public String login(UserRequestDto userRequestDto) {
        // login
        LoginUserDetails loginUserDetails = userDetailsService.loadUserByPhoneNumber(userRequestDto.getPhoneNumber());
        if (Objects.isNull(loginUserDetails) ||
                !passwordEncoder.matches(userRequestDto.getPassword(), loginUserDetails.getPassword())) {
            return"failedWrong user name or password";

        }

       //Update the Security logon user object
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //generate token
        String token = jwtTokenUtil.generateToken(loginUserDetails);

        Map<String, String> map = new HashMap<>();

        map.put("token", token);

        map.put("tokenHead", tokenHeader);

        return map.toString();

    }
}
