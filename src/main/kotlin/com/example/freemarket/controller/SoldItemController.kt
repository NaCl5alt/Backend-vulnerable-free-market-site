package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.RequestSoldItem
import com.example.freemarket.model.SoldItem
import com.example.freemarket.repo.SoldItemRepository
import com.example.freemarket.repo.UsersRepository
import com.example.freemarket.service.ItemService
import com.example.freemarket.service.SoldItemService
import com.example.freemarket.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class SoldItemController(private val userservice: UserService, private val solditemservice: SoldItemService, private val itemservice: ItemService) {
    @PostMapping("/solditem")
    fun regist(@RequestBody reqitem: RequestSoldItem): ResponseEntity<RequestSoldItem>{
        logger.info("regist solditem")

        val user1 = userservice.findByUserid(reqitem.exhibitorid)?:return ResponseEntity(HttpStatus.BAD_REQUEST)
        val user2 = userservice.findByUserid(reqitem.buyerid)?:return ResponseEntity(HttpStatus.BAD_REQUEST)
        val item1 = itemservice.findById(reqitem.id)?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        itemservice.deleteById(reqitem.id)

        val item2 = SoldItem(reqitem.name,reqitem.explanation,user1,user2,reqitem.price,reqitem.img)
        solditemservice.save(item2)

        return ResponseEntity(HttpStatus.CREATED)
    }
    @GetMapping("/solditem")
    fun findAll() = solditemservice.findAll()

    @GetMapping("/solditem/{id}")
    fun findById(@PathVariable id: UUID) = solditemservice.findById(id)
}