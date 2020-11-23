package com.example.freemarket.repo

import com.example.freemarket.model.SoldItem
import com.example.freemarket.model.Users
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SoldItemRepository : CrudRepository<SoldItem, String> {
    fun findByName(name: String): Iterable<SoldItem>
    fun findById(id: UUID): SoldItem
    fun findByExhibitor(user: Users): Iterable<SoldItem>
    fun findByBuyer(user: Users): Iterable<SoldItem>
}