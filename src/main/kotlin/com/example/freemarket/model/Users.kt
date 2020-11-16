package com.example.freemarket.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class Users(
        @get:JsonIgnore
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID = UUID.randomUUID(),
        @Column(name = "user_id", nullable = false, unique = true)
        var userid: String = "",
        @Column(name = "name", nullable = false)
        var name: String = "",
        @Column(name = "profile")
        var profile: String = "",
        @Column(name = "img")
        var img: String = "",
        @Column(nullable = false, updatable = false)
        var createdAt: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now()
)

data class RegistUser(
        var userid: String = "",
        var pass: String = "",
        var name: String = ""
)