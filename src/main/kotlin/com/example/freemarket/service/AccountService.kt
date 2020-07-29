package com.example.freemarket.service

import com.example.freemarket.model.Account
import com.example.freemarket.model.Users

interface AccountService {
    fun save(account: Account)
    fun findByUsers(users: Users): Account?
}