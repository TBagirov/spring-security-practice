package org.security.practic.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_HEADER_NAME = "Authorization";

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER_NAME);

        logger.debug("Authorization Header: {}", authHeader);

        if(authHeader != null && authHeader.startsWith(BEARER_PREFIX))
            return authHeader.substring(BEARER_PREFIX.length());
        return null;
    }

    public String generateTokenFromUsername(UserDetails userDetails){
        String username = userDetails.getUsername();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts
                .parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            logger.debug("Validate JWT Token: {}", authToken);
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch(MalformedJwtException e){
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT Token: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


}
