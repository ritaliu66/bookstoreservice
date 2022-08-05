package com.epam.bookstoreservice.util;

import com.epam.bookstoreservice.config.security.UserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The tool that gets the current user
 */
public class AuthUtil {

    public static UserDetail getCurrentUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();
        return (UserDetail) authenticationToken.getPrincipal();
    }

}
