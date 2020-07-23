package com.example.freemarket.model

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "item")
class Item(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID,
        var name: String,
        var explanation: String,
        @ManyToOne
        @JoinTable(
                name = "user_item",
                joinColumns = arrayOf(JoinColumn(name = "item_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "user_id"))
        )
        var exhibitor: Users,
        var price: Int,
        var img: String
) : Serializable/*{
    constructor(): this(UUID.randomUUID(),"","", Users(),0,"")
}*/