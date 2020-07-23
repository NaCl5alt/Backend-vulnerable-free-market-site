package com.example.freemarket.controller

import com.example.freemarket.model.Item
import com.example.freemarket.repo.ItemRepository
import com.example.freemarket.repo.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ItemController {
    @Autowired
    lateinit var repository: ItemRepository

    @Autowired
    lateinit var repository2: UsersRepository

    @RequestMapping("/item")
    fun findAll() = repository.findAll()

    @RequestMapping("/item/save")
    fun save(): String {
        val user = repository2.findByUserid("test")
        if (user == null) return "user null"
        var item = Item(UUID.randomUUID(), "test", "test", user, 100, "test")
        repository.save(item)
        return "Done"
    }

    @RequestMapping("/item/{id}")
    fun findById(@PathVariable id: UUID) = repository.findById(id)
}