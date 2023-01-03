package com.security.springbootnewsecurity.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.springbootnewsecurity.model.Role;
import com.security.springbootnewsecurity.model.User;
import com.security.springbootnewsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    private UserService userService;

     String token;
     String username;
     String password;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!hasAuthorizationBasic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

         token = getAccessToken(request);

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBasic(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Basic")) {
            return false;
        }

        return true;
    }
    public String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
        username = decodedString.split(":")[0];
        password = decodedString.split(":")[1];


        UserDetails userDetails = userService.loadUserByUsername(username);
        return userDetails;



    }



}

    /*
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

		*/
/*
		For our example we simply read the user and password information from the header and check if against our
		internal user store.
		IMPORTANT: THIS IS NOT A SECURE WAY TO DO AUTHENTICATION AND JUST FOR DEMONSTRATION PURPOSE!
		 *//*

        // First read the custom headers

        String auth = request.getHeader("Authorization");

        if (auth != null && auth.startsWith("Basic ")) {
            String token = auth.substring(6);

            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedString = new String(decodedBytes);
            System.out.println(decodedString);
            user = decodedString.split(":")[0];
            password = decodedString.split(":")[1];



        }


        // No we check if they are existent in our internal user store
        UserDetails userDetails = userService.loadUserByUsername(user);

        // When they are present we authenticate the user in the SecurityContextHolder
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // In either way we continue the filter chain to also apply filters that follow after our own.
        chain.doFilter(request, response);
    }
}*/
