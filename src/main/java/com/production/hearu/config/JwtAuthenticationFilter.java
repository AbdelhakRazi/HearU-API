package com.production.hearu.config;

import com.production.hearu.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    This class will check if we have JWT token in our http request
 **/
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, // our request
            @NonNull HttpServletResponse response,// intercept every request and extract data from request, and provide new data within response
            @NonNull FilterChain filterChain)// Chain of responisbility design pattern that contains list of other filters, can call next filter in the chain
            throws ServletException, IOException {
        // usually jwt token is passed in Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response); // means we don't want to continue with this filter if we don't have jwt
            return;
        }
        jwt = authHeader.substring(7); // Authorization Bearer .... and extract email from jwt token
        userEmail = jwtService.extractUserEmail(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // means user is not authenticated yet
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                ); // creating token
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
            }// first used for filtering http requests coming, it should be active and fired when we get the request
// fired once per request
}
