package com.example.freemarket.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class Users(
        @Id @Column(name = "user_id", nullable = false, unique = true)
        var userid: String = "",
        @Column(name = "name", nullable = false)
        var name: String = "",
        @Column(name = "profile")
        var profile: String = "",
        @Column(name = "img")
        var img: String = "",
        @Column(nullable = false, updatable = false)
        var created_at: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var updated_at: LocalDateTime = LocalDateTime.now()
)

data class RegistUser(
        var userid: String = "",
        var pass: String = "",
        var name: String = ""
)