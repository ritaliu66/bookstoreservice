package com.epam.bookstoreservice.security.jwt;

import com.epam.bookstoreservice.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
     * @param userEntity
     * @return
     */
    public String generateToken(UserEntity userEntity) {

        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userEntity.getPhoneNumber());
        claims.put(CLAIM_KEY_CREATE, new Date());

        return generateToken(claims);
    }


    public String getPhoneNumberFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (Objects.isNull(claims)) {
            return null;
        }

        return claims.getSubject();
    }

    /**
     * Check whether the token is valid
     *
     * @param token
     * @param userEntity
     * @return
     */
    public Boolean validateToken(String token, UserEntity userEntity) {
        String phoneNumberFromToken = getPhoneNumberFromToken(token);
        return phoneNumberFromToken.equals(userEntity.getPhoneNumber()) && isTokenNotExpired(token);
    }

    /**
     * Check whether the token is invalid
     *
     * @param token
     * @return
     */
    private boolean isTokenNotExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        if (Objects.isNull(expireDate)) {
            return true;
        }
        return expireDate.after(new Date());
    }

    /**
     * Check whether the token is refreshed
     *
     * @param token
     * @return
     */
    public Boolean canRefresh(String token) {
        return !isTokenNotExpired(token);
    }

    /**
     * Method for refreshing a token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (Objects.isNull(claims)) {
            return null;
        }
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
        if (Objects.isNull(claims)) {
            return null;
        }
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
