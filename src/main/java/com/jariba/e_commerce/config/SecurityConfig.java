package com.jariba.e_commerce.config;


import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Security;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {

//        httpSecurity.csrf(customizer -> customizer.disable());
//        httpSecurity.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated());
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.logout(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();

       return  httpSecurity.csrf(customizer -> customizer.disable())
               //.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .authorizeHttpRequests(customizer -> customizer.requestMatchers("/user/register","/user/login").permitAll().anyRequest().authenticated())
                //.formLogin(Customizer.withDefaults())
               //.formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


//    // to validate using userName and password (Session ID method)
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setPasswordEncoder(new BCryptPasswordEncoder(10));
//        //auth.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        System.out.println("In AuthenticationProvider");
//        auth.setUserDetailsService(userService);
//        return auth;
//    }
}
