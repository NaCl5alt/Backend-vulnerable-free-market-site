package com.example.freemarket.service

import com.example.freemarket.model.SoldItem
import java.util.*

interface SoldItemService {
    fun findByName(name: String): Iterable<SoldItem>
    fun findById(id: UUID): SoldItem
    fun findAll(): Iterable<SoldItem>
    fun save(item: SoldItem)
}