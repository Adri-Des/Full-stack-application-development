package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.openclassrooms.mddapi.security.jwt.JwtAuthenticationFilter;


/**
 * Configuration class for Spring Security in the application.
 * <p>
 * This class defines beans related to authentication, password encoding, and HTTP security filter chains,
 * including JWT authentication and stateless session management.
 * </p>
 */
@Configuration
public class WebSecurityConfig {
	
	  /**
     * Bean definition for the PasswordEncoder.
     * <p>
     * Uses BCryptPasswordEncoder to encode passwords securely.
     * </p>
     *
     * @return a BCryptPasswordEncoder instance.
     */
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	    }
	  
	  
	  /**
	     * Bean definition for the AuthenticationManager.
	     * <p>
	     * Retrieves the AuthenticationManager from the given AuthenticationConfiguration.
	     * </p>
	     *
	     * @param authenticationConfiguration the authentication configuration from which to obtain the manager.
	     * @return the AuthenticationManager instance.
	     * @throws Exception if the authentication manager cannot be obtained.
	     */
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	  
	  
	  /**
	     * Configures the main SecurityFilterChain for the application.
	     * <p>
	     * - Disables CORS and CSRF protection.<br>
	     * - Configures the session management to be stateless.<br>
	     * - Allows unauthenticated access to "/api/auth/**" and "/api/subjects".<br>
	     * - Requires authentication for all other "/api/**" endpoints.<br>
	     * - Adds a JwtAuthenticationFilter for JWT-based authentication.
	     * </p>
	     *
	     * @param http the HttpSecurity to configure.
	     * @param authenticationManager the authentication manager used by the JWT filter.
	     * @return the configured SecurityFilterChain.
	     * @throws Exception if an error occurs during filter chain construction.
	     */
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
	   http.cors().and().csrf().disable()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      .authorizeHttpRequests().antMatchers("/api/auth/**", "/api/subjects","/v3/api-docs/**",
                  "/swagger-ui.html",
                  "/swagger-ui/**").permitAll()
	      .antMatchers("/api/**").authenticated()
	      .anyRequest().authenticated()
	      .and()
	      .addFilter(new JwtAuthenticationFilter(authenticationManager));
	   
	   return http.build();

	   
	  }
	}



