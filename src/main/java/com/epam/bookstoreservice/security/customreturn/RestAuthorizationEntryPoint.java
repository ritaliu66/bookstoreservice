package com.epam.bookstoreservice.security.customreturn;

import com.epam.bookstoreservice.dto.response.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Custom returns the result that the interface has no permission
 *
 * @author ljw
 */

@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(403);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(objectMapper.writeValueAsString(Result
                .error(403, "Insufficient permissions, please contact the administrator")));
        out.flush();
        out.close();
    }
}
