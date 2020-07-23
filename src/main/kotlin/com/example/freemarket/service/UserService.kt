package com.example.freemarket.service

import com.example.freemarket.model.Users

interface UserService {
    fun store(user: Users)
    fun findByUserid(userid: String): Users?
}