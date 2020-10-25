package com.example.freemarket.repo

import com.example.freemarket.model.Users
import org.springframework.data.repository.CrudRepository

interface UsersRepository : CrudRepository<Users, String> {
    fun findByUserid(userid: String): Users?
    fun deleteByUserid(userid: String)
    fun findByNameContaining(name: String): Iterable<Users>?
}