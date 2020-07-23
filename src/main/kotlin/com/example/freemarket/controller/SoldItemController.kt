package com.example.freemarket.controller

import com.example.freemarket.model.SoldItem
import com.example.freemarket.repo.SoldItemRepository
import com.example.freemarket.repo.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class SoldItemController {
    @Autowired
    lateinit var repository: SoldItemRepository

    @Autowired
    lateinit var repository2: UsersRepository

    @RequestMapping("/solditem")
    fun findAll() = repository.findAll()

    @RequestMapping("/solditem/save")
    fun save(): String {
        val user = repository2.findByUserid("test")
        if (user == null) return "user null"
        val user2 = repository2.findByUserid("test2")
        if (user2 == null) return "user2 null"
        var item = SoldItem(UUID.randomUUID(), "test", "test", user, user2, 100, "test")
        repository.save(item)
        return "Done"
    }

    @RequestMapping("/solditem/{id}")
    fun findById(@PathVariable id: UUID) = repository.findById(id)
}