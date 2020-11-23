package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.RequestSoldItem
import com.example.freemarket.model.SoldItem
import com.example.freemarket.service.ItemService
import com.example.freemarket.service.SoldItemService
import com.example.freemarket.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@RestController
class SoldItemController(private val userservice: UserService, private val solditemservice: SoldItemService, private val itemservice: ItemService) {
    val tokens = Token()

    @PostMapping("/solditem")
    fun regist(@CookieValue token: String?, @RequestBody reqitem: RequestSoldItem): ResponseEntity<RequestSoldItem> {
        logger.info("regist solditem")

        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val user = userservice.findByUserid(tokens.getUseridFromToken(token))
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        val item1 = itemservice.findById(reqitem.id) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

//        val item = Paths.get("img/item/" + item1.img)
//        val solditem = Paths.get("img/solditem/" + item1.img)
        val item = Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/item/" + item1.img)
        val solditem = Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/solditem/" + item1.img)

        if (Files.exists(item)) {
            Files.copy(item, solditem)
            Files.delete(item)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val item2 = SoldItem(item1.name, item1.explanation, item1.exhibitor, user, item1.price, item1.img)
        solditemservice.save(item2)
        itemservice.deleteById(reqitem.id)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/solditem/exhibitor")
    fun findByExhibitor(@CookieValue token: String?): ResponseEntity<Iterable<SoldItem>> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val user = userservice.findByUserid(tokens.getUseridFromToken(token))
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        return ResponseEntity(solditemservice.findByExhibitor(user), HttpStatus.OK)
    }

    @GetMapping("/solditem/buyer")
    fun findByBuyer(@CookieValue token: String?): ResponseEntity<Iterable<SoldItem>> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        val user = userservice.findByUserid(tokens.getUseridFromToken(token))
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        return ResponseEntity(solditemservice.findByBuyer(user), HttpStatus.OK)
    }

    @GetMapping("/solditem/{id}")
    fun findById(@PathVariable id: UUID) = solditemservice.findById(id)
}