package com.epam.bookstoreservice.security.jwt;


import com.epam.bookstoreservice.entity.UserEntity;

import com.epam.bookstoreservice.security.userdetailsservice.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Login authorization filter for Jwt
 *
 * @author ljw
 */
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(tokenHeader);
        //There is a token
        if (Objects.nonNull(authHeader) && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            String phoneNumberFromToken = jwtTokenUtil.getPhoneNumberFromToken(authToken);

            //The token has a user name but is not logged in
            if (Objects.nonNull(phoneNumberFromToken) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                //login
                UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumberFromToken);

                //Check whether the token is valid and reset the user object parameters
                if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(authToken, (UserEntity) userDetails))) {
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
