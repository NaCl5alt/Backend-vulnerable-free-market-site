package com.example.freemarket.repo

import com.example.freemarket.model.Item
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ItemRepository : CrudRepository<Item, String> {
    fun findByName(name: String): Iterable<Item>
    fun findById(id: UUID): Item
}