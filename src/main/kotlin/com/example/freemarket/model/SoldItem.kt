package com.example.freemarket.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "solditem")
class SoldItem(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID,
        var name: String,
        var explanation: String,
        @ManyToOne
        @JoinTable(
                name = "ex_solditem",
                joinColumns = arrayOf(JoinColumn(name = "solditem_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "user_id"))
        )
        var exhibitor: Users,
        @ManyToOne
        @JoinTable(
                name = "buy_solditem",
                joinColumns = arrayOf(JoinColumn(name = "solditem_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "user_id"))
        )
        var buyer: Users,
        var price: Int,
        var img: String
) : Serializable/*{
    private constructor(): this(UUID.randomUUID(),"","", Users(),Users(),0,"")
}*/