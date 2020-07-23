package com.example.freemarket.repo

import com.example.freemarket.model.SoldItem
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SoldItemRepository : CrudRepository<SoldItem, String> {
    fun findByName(name: String): Iterable<SoldItem>
    fun findById(id: UUID): SoldItem
}