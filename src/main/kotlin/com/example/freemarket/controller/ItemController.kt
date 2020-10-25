package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.Item
import com.example.freemarket.model.RequestItem
import com.example.freemarket.service.ItemService
import com.example.freemarket.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
class ItemController(private val userservice: UserService, private val itemservice: ItemService) {
    val tokens = Token()

    @PostMapping("/item")
    fun regist(@CookieValue token: String?, @RequestBody reqitem: RequestItem): ResponseEntity<RequestItem> {
        logger.info("regist item")

        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val user = userservice.findByUserid(reqitem.exhibitorid) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val item = Item(reqitem.name, reqitem.explanation, user, reqitem.price, reqitem.img)
        itemservice.save(item)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/item")
    fun findAll() = itemservice.findAll()

    @GetMapping("/item/paging")
    fun paging(@RequestParam time: String) = ResponseEntity(itemservice.paging(LocalDateTime.parse(time)), HttpStatus.OK)

    @GetMapping("/item/first")
    fun firstItem() = ResponseEntity(itemservice.firstItem(), HttpStatus.OK)

    @GetMapping("/item/count")
    fun count() = ResponseEntity(itemservice.count(), HttpStatus.OK)

    @GetMapping("/item/{id}")
    fun findById(@PathVariable id: UUID) = itemservice.findById(id)
}