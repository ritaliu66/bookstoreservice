package com.epam.bookstoreservice.security.customreturn;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Custom Returns the result of the account is not authorized.
 *
 * @author ljw
 */

@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    private static final String CHARACTER_ENCODING="UTF-8";
    private static final String CONTENT_TYPE="application/json";

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        httpServletResponse.setCharacterEncoding(CHARACTER_ENCODING);
        httpServletResponse.setContentType(CONTENT_TYPE);
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN
                , objectMapper.writeValueAsString(ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Insufficient permissions, please contact the administrator")));
    }
}
