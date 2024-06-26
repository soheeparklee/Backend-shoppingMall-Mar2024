package org.shoppingMall.config.security;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.service.exceptions.CustomAccessDeniedHandler;
import org.shoppingMall.service.exceptions.CustomAuthenticationEntryPoint;
import org.shoppingMall.web.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .csrf(c-> c.disable())
                .httpBasic(h-> h.disable())
                .formLogin(f-> f.disable())
                .rememberMe(r-> r.disable())
                .sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(a->
                        a
                                .requestMatchers("/resources/static/**", "/auth/sign-up", "/auth/login", "/auth/logout", "/auth/withdrawl").permitAll()
                                .requestMatchers("/test").hasRole("USER")
                )

                .exceptionHandling(e->{
                    e.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                    e.accessDeniedHandler(new CustomAccessDeniedHandler());
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
