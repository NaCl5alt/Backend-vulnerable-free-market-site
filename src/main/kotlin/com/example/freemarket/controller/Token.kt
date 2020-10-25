package com.example.freemarket.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.auth0.jwt.exceptions.JWTCreationException
import com.example.freemarket.logger
import com.example.freemarket.model.Users
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class Token {
    var privateKey = "4BR5ghA0pPMawFmu8rU7Gsv0NzSTWRjSZqhLBEJS"

    @GetMapping("/token")
    fun reflesh(@CookieValue token: String?): ResponseEntity<String> {
        if (token != null) {
            val newtoken = refreshToken(token) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
            var headers = HttpHeaders()
            headers.add("Content-Type", "application/json")
            return ResponseEntity("{\"token\": \"$newtoken\"}", headers, HttpStatus.CREATED)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/token")
    fun check(@CookieValue token: String?): ResponseEntity<String> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            verify = authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)
        return if (verify) ResponseEntity(HttpStatus.OK)
        else ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    fun refreshToken(token: String): String? {
        try {
            val algorithm: Algorithm = HMAC512(privateKey)
            val verifier: JWTVerifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .acceptExpiresAt(60 * 60 * 24)
                    .build()
            verifier.verify(token)
            return createToken(Users(userid = getUseridFromToken(token))) ?: return null
        } catch (e: Exception) {
            return null
        }
    }

    fun authenticateToken(token: String): Boolean {
        return try {
            val algorithm: Algorithm = HMAC512(privateKey)
            val verifier: JWTVerifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
            verifier.verify(token)
            true
        } catch (e: JWTCreationException) {
            logger.info("Invalid Token!")
            false
        }
    }

    fun createToken(user: Users): String? {
        lateinit var token: String
        val EXPIRATION_TIME = 1000 * 60 * 5
        val issuedAt = Date()
        val notBefore = Date(issuedAt.time)
        val expiresAt = Date(issuedAt.time + EXPIRATION_TIME)
        try {
            val algorithm: Algorithm = HMAC512(privateKey)
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userid", user.userid)
                    .withIssuedAt(issuedAt)
                    .withNotBefore(notBefore)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm)
        } catch (e: JWTCreationException) {
            return null
        }
        return token
    }

    fun getUseridFromToken(token: String): String {
        val decodedToken = JWT.decode(token)
        return decodedToken.getClaim("userid").asString()
    }
}