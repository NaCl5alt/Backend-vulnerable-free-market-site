package com.example.freemarket.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "solditem")
data class SoldItem(
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
        var img: String,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID= UUID.randomUUID()
){
    private constructor(): this("","", Users(),Users(),0,"",UUID.randomUUID())
}

data class RequestSoldItem(
        var name: String,
        var explanation: String,
        var exhibitorid: String,
        var buyerid: String,
        var price: Int,
        var img: String
)