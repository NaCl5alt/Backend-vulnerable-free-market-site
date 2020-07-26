package com.example.freemarket.service

import com.example.freemarket.model.Account
import com.example.freemarket.model.Users
import com.example.freemarket.repo.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountServiceImpl(private val repository: AccountRepository):AccountService {
    @Transactional(timeout = 10)
    override fun save(account: Account) {
        repository.save(account)
    }
}