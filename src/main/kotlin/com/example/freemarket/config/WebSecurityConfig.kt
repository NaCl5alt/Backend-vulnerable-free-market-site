package com.example.freemarket.config

import com.example.freemarket.service.JpaAccountDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/error/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                //.anyRequest().permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").authenticated()
                .antMatchers("/user", "/login", "/item/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("userid")
                .passwordParameter("pass")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout").permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
    }

    @Configuration
    protected class AuthenticationConfiguration(private var accountDetailsService: JpaAccountDetailsServiceImpl) : GlobalAuthenticationConfigurerAdapter() {
        @Throws(Exception::class)
        override fun init(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService<UserDetailsService?>(accountDetailsService)
                    .passwordEncoder(BCryptPasswordEncoder())
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    fun authenticationEntryPoint(): AuthenticationEntryPoint? {
        return SimpleAuthenticationEntryPoint()
    }

    fun accessDeniedHandler(): AccessDeniedHandler? {
        return SimpleAccessDeniedHandler()
    }

    fun authenticationSuccessHandler(): AuthenticationSuccessHandler? {
        return SimpleAuthenticationSuccessHandler()
    }

    fun authenticationFailureHandler(): AuthenticationFailureHandler? {
        return SimpleAuthenticationFailureHandler()
    }

    fun logoutSuccessHandler(): LogoutSuccessHandler? {
        return HttpStatusReturningLogoutSuccessHandler()
    }

    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(CorsConfiguration.ALL)
        configuration.allowedHeaders = listOf(CorsConfiguration.ALL)
        configuration.allowedMethods = listOf(CorsConfiguration.ALL)
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}