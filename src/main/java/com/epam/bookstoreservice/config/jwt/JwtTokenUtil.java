package com.epam.bookstoreservice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Some tools related to JWT token
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATE = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * Generate a token based on user information
     *
     * @param userdetails
     * @return
     */
    public String generateToken(UserDetails userdetails) {

        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userdetails.getUsername());
        claims.put(CLAIM_KEY_CREATE, new Date());

        return generateToken(claims);
    }

    /**
     * Obtain the user name from the token
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String userName;

        try {
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }

        return userName;
    }

    /**
     * Check whether the token is valid
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);

        return userName.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    /**
     * Check whether the token is invalid
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);

        return expireDate.after(new Date());
    }

    /**
     * Check whether the token is refreshed
     *
     * @param token
     * @return
     */
    public Boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Method for refreshing a token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATE, new Date());
        return generateToken(claims);
    }

    /**
     * Obtain the token expiration time
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);

        return claims.getExpiration();
    }

    /**
     * Obtain the claims from the token
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }

    /**
     * Generate JWT token based on claims
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Generate the token expiration time
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
