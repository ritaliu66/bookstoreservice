package com.epam.bookstoreservice.security.customreturn;

import com.epam.bookstoreservice.dto.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom returns the result that the interface has no permission.
 *
 * @author ljw
 */

@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED
                , objectMapper.writeValueAsString("Not logged in. Please log in"));

    }
}
