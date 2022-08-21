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
import java.io.PrintWriter;

/**
 * Custom Returns the result of the account is not authorized.
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
        httpServletResponse.setStatus(401);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(objectMapper.writeValueAsString(Result
                .error(401, "The account is not authorized. Please try again")));
        out.flush();
        out.close();

    }
}
