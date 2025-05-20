package com.fresco.ecommerce.config;


import com.fresco.ecommerce.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jf;

    @Autowired
    AuthEntryPoint entryPoint;

    @Autowired
    UserAuthService udsi;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    @Bean
    public AuthenticationProvider authenProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(udsi);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        http.csrf( c-> c.disable())
                .authorizeHttpRequests( r -> r
                        .requestMatchers("/api/public/**","/h2-ui/**").permitAll()
                        .requestMatchers("/api/auth/consumer/**").hasAuthority("CONSUMER")
                        .requestMatchers("/api/auth/seller/**").hasAuthority("SELLER")
                        .anyRequest().authenticated())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling( x -> x.authenticationEntryPoint(entryPoint))
                .addFilterBefore(jf, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenProvider());

        http.headers(h->h.frameOptions(f->f.sameOrigin()));


        return http.build();

    }
}
