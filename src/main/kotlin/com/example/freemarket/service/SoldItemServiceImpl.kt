package com.example.freemarket.service

import com.example.freemarket.model.SoldItem
import com.example.freemarket.repo.SoldItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SoldItemServiceImpl(private val repository: SoldItemRepository): SoldItemService {
    override fun findByName(name: String): Iterable<SoldItem> {
        return repository.findByName(name)
    }

    override fun findById(id: UUID): SoldItem {
        return repository.findById(id)
    }

    override fun findAll(): Iterable<SoldItem> {
        return repository.findAll()
    }

    @Transactional(timeout = 10)
    override fun save(item: SoldItem) {
        repository.save(item)
    }
}