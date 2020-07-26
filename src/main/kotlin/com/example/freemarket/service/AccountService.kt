package com.example.freemarket.service

import com.example.freemarket.model.Account
import com.example.freemarket.model.Users
import org.springframework.security.core.userdetails.UserDetails

interface AccountService {
    fun save(account: Account)
    fun findByUsers(users: Users): Account?
}