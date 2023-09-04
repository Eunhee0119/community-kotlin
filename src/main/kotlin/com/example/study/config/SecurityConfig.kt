package com.example.study.config

import com.example.study.auth.token.JwtTokenFilter
import com.example.study.auth.token.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }


    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { formLogin -> formLogin.disable() }
            .headers { headers ->
                headers.frameOptions { option ->
                    option.sameOrigin()
                }
            }
            .httpBasic { httpBasic -> httpBasic.disable() }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/index", "/auth/signup", "/auth/signin").permitAll() // Allow public access
                    .requestMatchers("/members/*").hasRole("MEMBER")
                    .anyRequest().authenticated() // Require authentication for this endpoint
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .addFilterBefore(
                JwtTokenFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
//            .apply(JwtSecurityConfig(jwtTokenProvider));

        return http.build()
    }
}