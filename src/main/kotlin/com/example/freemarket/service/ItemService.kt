package com.example.freemarket.service

import com.example.freemarket.model.Item
import com.example.freemarket.model.Users
import java.time.LocalDateTime
import java.util.*

interface ItemService {
    fun findByName(name: String): Iterable<Item>
    fun findById(id: UUID): Item?
    fun findAll(): Iterable<Item>
    fun save(item: Item)
    fun deleteById(id: UUID)
    fun paging(time: LocalDateTime): Iterable<Item>
    fun firstItem(): Item
    fun count(): Long
    fun findByExhibitor(user: Users): Iterable<Item>
}