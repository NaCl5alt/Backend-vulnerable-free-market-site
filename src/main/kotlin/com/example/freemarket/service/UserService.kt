package com.example.freemarket.service

import com.example.freemarket.model.Users

interface UserService {
    fun save(user: Users)
    fun findAll(): Iterable<Users>?
    fun findByUserid(userid: String): Users?
    fun deleteByUserid(userid: String)
    fun findByNameContaining(name: String): Iterable<Users>?
}