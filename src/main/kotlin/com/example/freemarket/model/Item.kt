package com.example.freemarket.model

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "item")
data class Item(
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var explanation: String,
        @ManyToOne
        @JoinTable(
                name = "user_item",
                joinColumns = arrayOf(JoinColumn(name = "item_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "user_id"))
        )
        var exhibitor: Users,
        @Column(nullable = false)
        var price: Int,
        var img: String,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID=UUID.randomUUID()
) {
    constructor(): this("","", Users(),0,"",UUID.randomUUID())
}

data class RequestItem(
        var name: String,
        var explanation: String,
        var exhibitorid: String,
        var price: Int,
        var img: String
)