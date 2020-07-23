package com.example.freemarket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return NoOpPasswordEncoder.getInstance()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/error/**")
        //.antMatchers("/user/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .anyRequest().permitAll()
                //.antMatchers("/user/**").permitAll()
                //.antMatchers("/", "/login").permitAll()
                //.anyRequest().authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login")
                //.loginProcessingUrl("/login") // ログインフォームのアクションに指定したURL[action="@{/login}"]を設定
                //.usernameParameter("username") // ログインフォームのユーザー欄のname属性を設定
                //.passwordParameter("password") // ログインフォームのパスワード欄のname属性を設定
                //.successForwardUrl("/") // ログイン成功時に遷移するURL
                //.failureUrl("/login?error") // ログイン失敗時に遷移するURL
                //.permitAll()
                //.and()
                //.logout()
                //.logoutUrl("/logout")
                //.permitAll()
                //.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
        http.csrf().disable()
    }


}