package com.example.mobile.mobile.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/clients/signup", "/api/clients/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/clients/**").permitAll()
                .requestMatchers("/api/spectacles/**").permitAll()
                .requestMatchers("/api/representations/**").permitAll()
                .requestMatchers("/api/rubriques/**").permitAll()
                .requestMatchers("/api/artistes/**").permitAll()
                .requestMatchers("/api/billets/**").permitAll()
                .requestMatchers("/api/reservations/**").permitAll()
                .requestMatchers("/api/lieux/**").permitAll()

                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean<OpenEntityManagerInViewFilter> openEntityManagerInViewFilter() {
        FilterRegistrationBean<OpenEntityManagerInViewFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new OpenEntityManagerInViewFilter());
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }
}
