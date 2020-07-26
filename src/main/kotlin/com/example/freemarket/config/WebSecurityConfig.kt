package com.example.freemarket.config

import com.example.freemarket.service.JpaAccountDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/error/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                //.anyRequest().permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").authenticated()
                .antMatchers("/user","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("userid")
                .passwordParameter("pass")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
        http.csrf().disable()
    }



    @Configuration
    protected class AuthenticationConfiguration : GlobalAuthenticationConfigurerAdapter() {
        @Autowired
        var accountDetailsService: JpaAccountDetailsServiceImpl? = null

        @Throws(Exception::class)
        override fun init(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService<UserDetailsService?>(accountDetailsService)
                    .passwordEncoder(BCryptPasswordEncoder())
        }
    }
}