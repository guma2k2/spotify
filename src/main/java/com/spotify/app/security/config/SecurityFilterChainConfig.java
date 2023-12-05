package com.spotify.app.security.config;

import com.spotify.app.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private  HandlerExceptionResolver exceptionResolver ;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter () {
        return new JwtAuthenticationFilter(exceptionResolver);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((AbstractHttpConfigurer::disable))
                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(
//                        authz ->
//                                authz
//                                        .requestMatchers(
//                                                "/api/v1/song/save",
//                                                "/api/v1/song/update/**",
//                                                "/api/v1/song/upload/**",
//                                                "/api/v1/album/upload/**",
//                                                "/api/v1/album/*/add/**",
//                                                "/api/v1/album/*/remove/**",
//                                                "/api/v1/album/*/add",
//                                                "/api/v1/album/update/**"
//                                        )
//                                        .hasRole( "ARTIST")
//                                        .requestMatchers(
//                                                "/api/v1/role/**",
//                                                "/api/v1/playlist/admin/**",
//                                                "/api/v1/category/admin/**",
//                                                "/api/v1/review/admin/**"
//                                        )
//                                        .hasRole("ADMIN")
//                                        .requestMatchers(
//                                                "/api/v1/song/find/by/sentiment/**",
//                                                "/api/v1/song/increase/view/**",
//                                                "/api/v1/user/increase/view/**",
//                                                "/api/v1/user/*/playlists/followings",
//                                                "/api/v1/user/*/add/**",
//                                                "/api/v1/user/*/remove/**",
//                                                "/api/v1/playlist/user/*/add/**",
//                                                "/api/v1/playlist/user/*/remove/**",
//                                                "/api/v1/playlist/*/create/playlist",
//                                                "/api/v1/playlist/*/add/song/**",
//                                                "/api/v1/playlist/*/remove/song/**",
//                                                "/api/v1/playlist/upload/**",
//                                                "/api/v1/follower/*/follow/**",
//                                                "/api/v1/follower/*/cancel/**",
//                                                "/api/v1/follower/*/followings",
//                                                "/api/v1/follower/is/*/followed/**",
//                                                "/api/v1/review/*/review/in/**"
//                                        )
//                                        .authenticated()
//                                        .anyRequest()
//                                        .permitAll())
//                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/api/v1/auth/logout")
//                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .csrf((AbstractHttpConfigurer::disable))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/api/v1/allowAllByPhi/**").authenticated().anyRequest().permitAll())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
        ;
        return http.build();
    }

}
