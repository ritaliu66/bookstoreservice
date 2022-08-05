package com.epam.bookstoreservice.service.impl;

import com.epam.bookstoreservice.config.jwt.JwtTokenUtil;
import com.epam.bookstoreservice.config.security.UserDetail;
import com.epam.bookstoreservice.entity.UserEntity;
import com.epam.bookstoreservice.service.LoginService;
import com.epam.bookstoreservice.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    private final UserDetailsService userDetailsService;


    private final PasswordEncoder passwordEncoder;


    private final JwtTokenUtil jwtTokenUtil;

    private static final String tokenHeader = "Bearer";

    @Override
    public String login(UserEntity userEntity) {
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());
        if (Objects.isNull(userDetails) ||
                !passwordEncoder.matches(userEntity.getPassword(), userDetails.getPassword())) {
            return"failedWrong user name or password";

        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);

        Map<String, String> map = new HashMap<>();

        map.put("token", token);

        map.put("tokenHead", tokenHeader);

        return map.toString();


    }
}
