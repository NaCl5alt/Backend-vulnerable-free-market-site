package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.Users
import com.example.freemarket.repo.UsersRepository
import com.example.freemarket.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.*


@RestController
class UserController(private val userservice: UserService) {
    @PostMapping("/user"/*, produces = [MediaType.TEXT_PLAIN_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE]*/)
    fun regist(@RequestBody user: Users): ResponseEntity<Users> {
        logger.info("regist user")

        val res = userservice.findByUserid(user.userid)
        if (res != null) return ResponseEntity(HttpStatus.BAD_REQUEST)

        user.pass = BCrypt.hashpw(user.pass, BCrypt.gensalt())

        userservice.save(user)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/user")
    fun findAll() = userservice.findAll()

    @RequestMapping("/user/{userid}")
    fun findByUserid(@PathVariable userid: String) = userservice.findByUserid(userid)
}