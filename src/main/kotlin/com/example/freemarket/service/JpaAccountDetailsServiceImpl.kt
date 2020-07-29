package com.example.freemarket.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component


@Component
class JpaAccountDetailsServiceImpl(private val accountService: AccountService, private val userService: UserService) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userid: String): UserDetails? {
        return userService.findByUserid(userid)?.let { accountService.findByUsers(it) }
    }
}