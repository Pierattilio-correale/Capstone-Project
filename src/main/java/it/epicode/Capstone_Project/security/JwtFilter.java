package it.epicode.Capstone_Project.security;


import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.UnauthorizedException;
import it.epicode.Capstone_Project.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

//

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");


        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token non presente , non sei autorizzato ad usare il servizio richiesto");

        } else {

            String token = authorization.substring(7);


            jwtTool.validateToken(token);

            try {
                User user = jwtTool.getUserFromToken(token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (NotFoundException e) {
                throw new UnauthorizedException("l'utente collegato al token non trovato");
            }

            filterChain.doFilter(request, response);

        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();

        AntPathMatcher pathMatcher = new AntPathMatcher();

        if (method.equalsIgnoreCase("GET")) {

            return true;
        }


        if (pathMatcher.match("/auth/**", path)) {
            return true;
        }


        return false;
    }
}

