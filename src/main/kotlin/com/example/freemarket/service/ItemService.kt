package com.example.freemarket.service

import com.example.freemarket.model.Item
import java.util.*

interface ItemService {
    fun findByName(name: String): Iterable<Item>
    fun findById(id: UUID): Item
    fun findAll(): Iterable<Item>
    fun save(item: Item)
}