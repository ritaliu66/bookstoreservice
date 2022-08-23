package com.epam.bookstoreservice.security.customreturn;

import com.epam.bookstoreservice.dto.response.Result;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN
                , Result.error(HttpServletResponse.SC_FORBIDDEN,"Insufficient permissions, please contact the administrator").toString());
    }
}
