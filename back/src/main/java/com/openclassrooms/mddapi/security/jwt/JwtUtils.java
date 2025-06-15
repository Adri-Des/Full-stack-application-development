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


/**
 * Utility class for handling JSON Web Tokens (JWT) within the application.
 * <p>
 * Provides methods for generating JWTs, validating them, and extracting information (such as user email)
 * from a token. Uses the HS256 algorithm for signing and verifying tokens.
 * </p>
 */
public class JwtUtils {
	 /**
     * Logger for logging JWT-related errors and information.
     */
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	/**
     * Secret key used for signing JWT tokens.
     */
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    /**
     * Token expiration time in milliseconds (1 day).
     */
    private static final long EXPIRATION_TIME = 86400000; 

    /**
     * Generates a JWT token for the given email.
     * <p>
     * The token's subject is set to the provided email, and its expiration time is set to 24 hours from creation.
     * </p>
     *
     * @param email the user's email address to include in the token subject
     * @return the generated JWT as a String
     */
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
    }

    
    /**
     * Validates the provided JWT token.
     * <p>
     * Checks the token's signature, format, expiration, and support. Logs detailed error messages if validation fails.
     * </p>
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
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
    

    /**
     * Extracts the email (subject) from the given JWT token.
     * <p>
     * Parses the token and retrieves the subject, which is assumed to be the user's email address.
     * </p>
     *
     * @param token the JWT token from which to extract the email
     * @return the email address contained in the token's subject
     * @throws RuntimeException if the token is invalid or cannot be parsed
     */
    public static String extractEmail(String token) {
         try {
         return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token)
                .getBody().getSubject();
        }catch (Exception e) {
            throw new RuntimeException("Token invalide");
        }
    }
    
    
}
