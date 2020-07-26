package com.example.freemarket.repo

import com.example.freemarket.model.Account
import com.example.freemarket.model.Users
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface AccountRepository:CrudRepository<Account, String>{
    fun findByUsers(users: Users): Account?
}