package com.example.freemarket.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account")
data class Account(
        @get:JsonIgnore
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID = UUID.randomUUID(),
        @get:JsonIgnore
        @Column(name = "pass", nullable = false)
        var pass: String? = "",
        @get:JsonIgnore
        var enabled: Boolean = true,
        var roleType: String = "ROLE_USER",
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "userid")
        var users: Users = Users(),
        @Column(nullable = false, updatable = false)
        var created_at: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var updated_at: LocalDateTime = LocalDateTime.now()
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(this.roleType)
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String? {
        return this.users.userid
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String? {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}