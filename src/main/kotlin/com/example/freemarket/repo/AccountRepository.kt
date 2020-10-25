package com.example.freemarket.repo

import com.example.freemarket.model.Account
import com.example.freemarket.model.Users
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, String> {
    fun findByUsers(users: Users): Account?
    fun deleteByUsers(user: Users)
}