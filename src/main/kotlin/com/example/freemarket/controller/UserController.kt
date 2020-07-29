package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.Account
import com.example.freemarket.model.RegistUser
import com.example.freemarket.model.Users
import com.example.freemarket.service.AccountService
import com.example.freemarket.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val accountservice: AccountService, private val userservice: UserService, private val passwordEncoder: PasswordEncoder) {
    @PostMapping("/user"/*, produces = [MediaType.TEXT_PLAIN_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE]*/)
    fun regist(@RequestBody user: RegistUser): ResponseEntity<Users> {
        logger.info("regist user")

        val res = userservice.findByUserid(user.userid)
        if (res != null) return ResponseEntity(HttpStatus.BAD_REQUEST)

        user.pass = passwordEncoder.encode(user.pass)

        accountservice.save(Account(pass = user.pass, roleType = "ROLE_USER", users = Users(userid = user.userid, name = user.name)))
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/user")
    fun findAll() = userservice.findAll()

    @RequestMapping("/user/{userid}")
    fun findByUserid(@PathVariable userid: String) = userservice.findByUserid(userid)
}