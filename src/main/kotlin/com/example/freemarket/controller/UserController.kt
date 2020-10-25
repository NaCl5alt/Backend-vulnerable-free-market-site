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
    val tokens = Token()

    @PostMapping("/user")
    fun regist(@RequestBody user: RegistUser): ResponseEntity<Users> {
        logger.info("regist user")

        val res = userservice.findByUserid(user.userid)
        if (res != null) return ResponseEntity(HttpStatus.BAD_REQUEST)

        user.pass = passwordEncoder.encode(user.pass)

        accountservice.save(Account(pass = user.pass, roleType = "ROLE_USER", users = Users(userid = user.userid, name = user.name)))
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/user")
    fun findByUseridFromToken(@CookieValue token: String?): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val userid = tokens.getUseridFromToken(token)

        return if (verify) ResponseEntity(userservice.findByUserid(userid), HttpStatus.OK)
        else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/user/{userid}")
    fun findByUserid(@CookieValue token: String?, @PathVariable userid: String): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        return if (verify) ResponseEntity(userservice.findByUserid(userid), HttpStatus.OK)
        else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    @DeleteMapping("/user")
    fun deleteByUserid(@CookieValue token: String?): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val userid = tokens.getUseridFromToken(token)
        val user = userservice.findByUserid(userid) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        return if (verify) {
            accountservice.deleteByUsers(user)
            userservice.deleteByUserid(userid)
            ResponseEntity(HttpStatus.OK)
        } else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/user/edit")
    fun changeByUsernameAndProfile(@CookieValue token: String?, @RequestParam name: String, @RequestParam profile: String, @RequestParam img: String): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        var user = userservice.findByUserid(tokens.getUseridFromToken(token))
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        if (name.isEmpty() or name.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)

        user.name = name
        user.profile = profile
        user.img = img

        return if (verify) {
            userservice.save(user)
            ResponseEntity(HttpStatus.OK)
        } else ResponseEntity(HttpStatus.UNAUTHORIZED)

    }

    @PostMapping("/user/name")
    fun changeByUsername(@CookieValue token: String?, @RequestParam name: String): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        var user = userservice.findByUserid(tokens.getUseridFromToken(token))
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        user.name = name

        return if (verify) {
            userservice.save(user)
            ResponseEntity(HttpStatus.OK)
        } else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/user/id")
    fun changeByUserid(@CookieValue token: String?, @RequestParam userid: String): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        var user = userservice.findByUserid(tokens.getUseridFromToken(token))
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        user.userid = userid

        return if (verify) {
            userservice.save(user)
            ResponseEntity(HttpStatus.OK)
        } else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/user/passwd")
    fun changeByPasswd(@CookieValue token: String?, @RequestParam passwd: String): ResponseEntity<Users> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val userid = tokens.getUseridFromToken(token)
        val user = userservice.findByUserid(userid) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        var account = accountservice.findByUsers(user) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        account.pass = passwordEncoder.encode(passwd)

        return if (verify) {
            accountservice.save(account)
            ResponseEntity(HttpStatus.OK)
        } else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
}