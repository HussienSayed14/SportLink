package com.SportsLink.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The OncePerRequestFilter indicates that this filter will be called once when a request comes in.
 * This filter will be used to intercept all requests and check if the request has a valid JWT token.
 * If the token is valid, the request will be allowed to pass through.
 * If the token is invalid, the request will be blocked.
 * This filter will be added to the Spring Security filter chain.
 * The filter chain is a series of filters that are applied to the request.
 * */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {  // Assuming the JWT token is stored in a cookie named "token"
                    return cookie.getValue();  // Return the JWT token
                }
            }
        }
        return null;  // No JWT token found in cookies
    }



    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        /** Extract the JWT from the HttpOnly cookie */
        final String jwtToken = getJwtFromCookies(request);

        final String userPhone;
        /** If the jwt Token extracted from the cookies
         * then the request will be allowed to pass through to the next filter in the filter chain.
          */

        if(jwtToken == null ) {
            // If the token is null, return a 403 response and stop the filter chain
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 status code
            response.getWriter().write("{\"message\": \"You are not authorized to access this resource, please login first\"}");
            return;

//            filterChain.doFilter(request, response);
        }


        try{

            userPhone = jwtService.extractUserPhone(jwtToken);
            /** This means that the user is NOT already authenticated */
            if(userPhone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = this.userDetailsService.loadUserByUsername(userPhone);
                /** If the JWT token is valid, then the user will be authenticated and update Security context*/
                if(jwtService.isTokenValid(jwtToken,user)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else{
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
                    response.getWriter().write("{\"message\": \"Your session has expired, please login again\"}");
                    return;
                }
            }
        }catch (Exception e){
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
            response.getWriter().write("{\"message\": \"Your session has expired, please login again\"}");
            return;
        }
        filterChain.doFilter(request,response);


    }
}
