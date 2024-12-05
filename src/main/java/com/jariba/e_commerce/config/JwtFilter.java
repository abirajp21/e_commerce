package com.jariba.e_commerce.config;


import com.jariba.e_commerce.Model.User;
import com.jariba.e_commerce.Service.JwtService;
import com.jariba.e_commerce.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserService userService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        UserDetails user = null;

        if(request.getRequestURL().toString().contains("/login")){
            filterChain.doFilter(request,response);
            return;
        }


        if (authToken != null && authToken.startsWith("Bearer ")) {

            token = authToken.substring(7);
            userName = jwtService.getUserName(token);
            System.out.println(userName);
        }

        if(userName!= null) {
            try {
                user = userService.loadUserByUsername(userName);
            }
            catch (Exception e)
            {
                user = null;
                blockFilter(response);
                return;
            }
        }
        else
        {
            blockFilter(response);
            return;
        }


        if( SecurityContextHolder.getContext().getAuthentication() != null)
        {
            filterChain.doFilter(request,response);
            return ;
        }

        if(jwtService.validateToken(token)) {

                try {
                    UsernamePasswordAuthenticationToken usrPasAuthToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                    usrPasAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usrPasAuthToken);
                    //System.out.println("in Filter Chain");
                    filterChain.doFilter(request,response);
                }
                catch (Exception e)
                {
                    blockFilter(response);
                    //System.out.println("Invalid username");
                    return ;
                }
            }
        else {
             blockFilter(response);
             return;
         }
    }



    private void blockFilter(HttpServletResponse response) throws IOException {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED BEARER TOKEN");

    }
}
