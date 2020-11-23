package com.example.freemarket.repo

import com.example.freemarket.model.Item
import com.example.freemarket.model.Users
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.*

interface ItemRepository : CrudRepository<Item, String> {
    fun findByName(name: String): Iterable<Item>
    fun findById(id: UUID): Item?
    fun deleteById(id: UUID)
    fun findTop50ByCreatedAtBeforeOrderByCreatedAtDesc(time: LocalDateTime): Iterable<Item>
    fun findTop1ByOrderByCreatedAtDesc(): Item
    fun findByExhibitor(user: Users): Iterable<Item>
}