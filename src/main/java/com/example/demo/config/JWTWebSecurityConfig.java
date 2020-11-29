package com.example.demo.config;

import com.example.demo.security.JwtTokenAuthorizationOncePerRequestFilter;
import com.example.demo.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private  final JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

    private final UserDetailsService jwtInMemoryUserDetailsService;

    private final JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

    @Value("${jwt.get.token.uri}")
    private String authenticationPath;

    @Autowired
    public JWTWebSecurityConfig(JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint, UserDetailsService jwtInMemoryUserDetailsService, JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter) {
        this.jwtUnAuthorizedResponseAuthenticationEntryPoint = jwtUnAuthorizedResponseAuthenticationEntryPoint;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/rest/api/v1/login", "/rest/api/v1/register").permitAll()
                .antMatchers(HttpMethod.GET,"/rest/api/v1/places/**").permitAll()
                .antMatchers(HttpMethod.POST,"/rest/api/v1/places/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/rest/api/v1/places/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/rest/api/v1/places/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/rest/api/v1/concerts/**").permitAll()
                .antMatchers(HttpMethod.POST,"/rest/api/v1/concerts/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/rest/api/v1/concerts/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/rest/api/v1/concerts/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/rest/api/v1/comments/**").permitAll()
                .antMatchers(HttpMethod.POST,"/rest/api/v1/comments/**").hasAuthority("USER");

        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl(); // disable caching
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoderBean());
        daoAuthenticationProvider.setUserDetailsService(jwtInMemoryUserDetailsService);
        return daoAuthenticationProvider;
    }
}