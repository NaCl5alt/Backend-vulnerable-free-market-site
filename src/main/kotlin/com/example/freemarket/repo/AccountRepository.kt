package com.example.freemarket.repo

import com.example.freemarket.model.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository:CrudRepository<Account, String>{}