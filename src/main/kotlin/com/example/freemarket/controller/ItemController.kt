package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.Item
import com.example.freemarket.model.RequestItem
import com.example.freemarket.service.ItemService
import com.example.freemarket.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class ItemController(private val userservice: UserService, private val itemservice: ItemService) {
    @PostMapping("/item")
    fun regist(@RequestBody reqitem: RequestItem): ResponseEntity<RequestItem> {
        logger.info("regist item")
        val user = userservice.findByUserid(reqitem.exhibitorid) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val item = Item(reqitem.name, reqitem.explanation, user, reqitem.price, reqitem.img)
        itemservice.save(item)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/item")
    fun findAll() = itemservice.findAll()

    @GetMapping("/item/{id}")
    fun findById(@PathVariable id: UUID) = itemservice.findById(id)
}