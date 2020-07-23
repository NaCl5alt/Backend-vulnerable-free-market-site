package com.example.freemarket.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class Users(
        @Column(name = "user_id", nullable = false, unique = true)
        var userid: String,
        @get:JsonIgnore
        @Column(name = "pass", nullable = false)
        var pass: String?,
        @Column(name = "name", nullable = false)
        var name: String,
        @Column(name = "profile")
        var profile: String,
        @Column(name = "img")
        var img: String,
        @get:JsonIgnore
        var enabled: Boolean = true,
        @get:JsonIgnore
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID = UUID.randomUUID()
) {
    constructor() : this("", "", "", "", "", true, UUID.randomUUID())
}