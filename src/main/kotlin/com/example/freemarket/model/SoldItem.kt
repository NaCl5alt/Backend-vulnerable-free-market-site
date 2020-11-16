package com.example.freemarket.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "solditem")
data class SoldItem(
        var name: String = "",
        var explanation: String = "",
        @ManyToOne
        @JoinTable(
                name = "ex_solditem",
                joinColumns = arrayOf(JoinColumn(name = "solditem_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "user_id"))
        )
        var exhibitor: Users = Users(),
        @ManyToOne
        @JoinTable(
                name = "buy_solditem",
                joinColumns = arrayOf(JoinColumn(name = "solditem_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "user_id"))
        )
        var buyer: Users = Users(),
        var price: Int = 0,
        var img: String = "",
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID = UUID.randomUUID(),
        @Column(nullable = false, updatable = false)
        var createdAt: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now()
)

data class RequestSoldItem(
        var id: UUID
)