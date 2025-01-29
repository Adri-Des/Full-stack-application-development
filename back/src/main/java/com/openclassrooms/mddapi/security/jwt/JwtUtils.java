package com.openclassrooms.mddapi.security.jwt;

/*import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;*/
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.openclassrooms.starterjwt.security.jwt.JwtUtils;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 jour

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
      /*  try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }*/
    	
    	 try {
    	      Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
    	      return true;
    	    } catch (SignatureException e) {
    	      logger.error("Invalid JWT signature: {}", e.getMessage());
    	    } catch (MalformedJwtException e) {
    	      logger.error("Invalid JWT token: {}", e.getMessage());
    	    } catch (ExpiredJwtException e) {
    	      logger.error("JWT token is expired: {}", e.getMessage());
    	    } catch (UnsupportedJwtException e) {
    	      logger.error("JWT token is unsupported: {}", e.getMessage());
    	    } catch (IllegalArgumentException e) {
    	      logger.error("JWT claims string is empty: {}", e.getMessage());
    	    }

    	    return false;
    	  }
    

    public static String extractEmail(String token) {
         try {
         return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token)
                .getBody().getSubject();
        }catch (Exception e) {
            throw new RuntimeException("Token invalide");
        }
    }
    
    
}
