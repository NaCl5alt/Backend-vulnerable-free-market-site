package com.example.freemarket.service

import com.example.freemarket.model.Account

interface AccountService {
    fun save(account: Account)
}