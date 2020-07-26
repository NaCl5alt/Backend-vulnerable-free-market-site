package com.example.freemarket.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class Users(
        @Id @Column(name = "user_id", nullable = false, unique = true)
        var userid: String="",
        @Column(name = "name", nullable = false)
        var name: String="",
        @Column(name = "profile")
        var profile: String="",
        @Column(name = "img")
        var img: String=""
)

data class RegistUser(
        var userid: String="",
        var pass: String="",
        var name: String=""
)