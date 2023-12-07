package com.mtsegab.photoapp.api.users.security;

import com.mtsegab.photoapp.api.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private Environment environment;

    private UsersService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(Environment environment, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.environment = environment;
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        //telling spring the service in my appl which contains that will be used to look up user in a db
        authenticationManagerBuilder.userDetailsService(usersService)
                                    .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //Create AutheniticationFilter - custom url
//        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager);
//        authenticationFilter.setFilterProcessesUrl("/customUrlHere"); //Do not hard code but get it from the app properties

        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                        .access(new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip") + "')")) //this is to allow requests from the gateway.
                .requestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")).permitAll())
                .addFilter(new AuthenticationFilter(usersService, environment, authenticationManager))
                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));
        return http.build();
    }
}
