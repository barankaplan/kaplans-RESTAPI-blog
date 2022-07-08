package com.kaplans.kaplansrestapiblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    //aims to guarantee a single execution per request dispatch

    @Autowired
    private  JWTTokenProvider jwtTokenProvider;
    @Autowired
    private  CustomerUserDetailService customerUserDetailService;




    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //get JWT -token from http request
        String token = getJWTFromToken(request);
        //validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            //get username from token
            String userName = jwtTokenProvider.getUserNameFromJWT(token);
            //load user associated with token
            UserDetails loadUserByUsername = customerUserDetailService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loadUserByUsername, null, loadUserByUsername.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //set spring security
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);


    }


    //bearer token ! our string !
    private String getJWTFromToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
