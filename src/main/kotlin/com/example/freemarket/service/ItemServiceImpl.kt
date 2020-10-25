package com.example.freemarket.service

import com.example.freemarket.model.Item
import com.example.freemarket.repo.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class ItemServiceImpl(private val repository: ItemRepository) : ItemService {
    override fun findByName(name: String): Iterable<Item> {
        return repository.findByName(name)
    }

    override fun findById(id: UUID): Item? {
        return repository.findById(id)
    }

    override fun findAll(): Iterable<Item> {
        return repository.findAll()
    }

    @Transactional(timeout = 10)
    override fun save(item: Item) {
        repository.save(item)
    }

    @Transactional(timeout = 10)
    override fun deleteById(id: UUID) {
        repository.deleteById(id)
    }

    override fun paging(time: LocalDateTime): Iterable<Item> {
        return repository.findTop50ByCreatedAtBeforeOrderByCreatedAtDesc(time)
    }

    override fun firstItem(): Item {
        return repository.findTop1ByOrderByCreatedAtDesc()
    }

    override fun count(): Long {
        return repository.count()
    }
}