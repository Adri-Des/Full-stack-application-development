package com.openclassrooms.mddapi.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Filter that handles JWT authentication for incoming HTTP requests.
 * <p>
 * This filter intercepts requests to check for a JWT in the Authorization header.
 * If a valid JWT is found, it extracts the user's email and sets up the Spring Security context.
 * If the JWT is missing or invalid, the filter either passes the request along or responds with a error.
 * </p>
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	 /**
     * Constructs a new JwtAuthenticationFilter with the given authentication manager.
     *
     * @param authManager the AuthenticationManager used for authentication.
     */
    public JwtAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    
    
    /**
     * Processes incoming HTTP requests to perform JWT authentication.
     * <p>
     * - If the "Authorization" header is not present or does not start with "Bearer ", the filter chain continues.<br>
     * - If a JWT is present, it attempts to extract the user's email and authenticate the user.<br>
     * - If authentication fails, responds with HTTP 403 Forbidden and an error message.
     * </p>
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws IOException      if an input or output exception occurs
     * @throws ServletException if a servlet exception occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            String email = JwtUtils.extractEmail(token);

            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token invalide : " + e.getMessage());
            return;
        }

        chain.doFilter(request, response);
    }
}
