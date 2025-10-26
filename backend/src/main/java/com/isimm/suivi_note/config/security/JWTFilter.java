package com.isimm.suivi_note.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.isimm.suivi_note.services.auth.JWTService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService; //injected via the userService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth")){
            filterChain.doFilter(request, response);
            return;
        }
        final String accessToken;
        final String userCin;
        accessToken = jwtService.getTextFromCookie(request,"accessToken");
        if(accessToken==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            userCin = jwtService.extractUserCin(accessToken);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }catch (JwtException e) {

            // Token invalide (mauvaise signature, format incorrect, etc.) → 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (userCin !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userdetails = this.userDetailsService.loadUserByUsername(userCin);
            if(this.jwtService.isTokenValid(userdetails.getUsername(), accessToken)){
                final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }






        // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
}
