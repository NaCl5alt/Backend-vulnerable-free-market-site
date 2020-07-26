package com.example.freemarket.service

import com.example.freemarket.repo.AccountRepository
import com.example.freemarket.repo.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
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