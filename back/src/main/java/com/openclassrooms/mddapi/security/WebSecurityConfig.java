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


@Configuration
public class WebSecurityConfig {
	
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	    }
	  
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	  
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
	   http.cors().and().csrf().disable()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      .authorizeHttpRequests().antMatchers("/api/auth/**", "/api/subjects").permitAll()
	      .antMatchers("/api/**").authenticated()
	      .anyRequest().authenticated()
	      .and()
	      .addFilter(new JwtAuthenticationFilter(authenticationManager));
	   
	   return http.build();

	   
	  }
	}



