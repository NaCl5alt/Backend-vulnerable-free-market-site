package com.example.freemarket.controller

import com.example.freemarket.filestorage.FileStorage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Controller
class imgController(private val fileStorage: FileStorage) {
    val tokens = Token()

    @PostMapping("/img/user")
    fun UpUserImg(@RequestParam("img") file: MultipartFile, @CookieValue token: String?): ResponseEntity<String> {

        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)

        return if (Regex("image/.*").containsMatchIn(file.contentType.toString())) {
            var ext = ""
            when (file.contentType.toString()) {
                "image/png" -> ext = ".png"
                "image/jpeg" -> ext = ".jpeg"
                "image/gif" -> ext = ".gif"
            }
            val filename = UUID.randomUUID().toString() + ext
            fileStorage.store(file, "user", filename)
            ResponseEntity(filename, HttpStatus.CREATED)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/img/item")
    fun UpItemImg(@RequestParam("img") file: MultipartFile, @CookieValue token: String?): ResponseEntity<String> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)
        return if (Regex("image/.*").containsMatchIn(file.contentType.toString())) {
            var ext = ""
            when (file.contentType.toString()) {
                "image/png" -> ext = ".png"
                "image/jpeg" -> ext = ".jpeg"
                "image/gif" -> ext = ".gif"
            }
            val filename = UUID.randomUUID().toString() + ext
            fileStorage.store(file, "item", filename)
            ResponseEntity(filename, HttpStatus.CREATED)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/img/solditem")
    fun UpSoldItemImg(@RequestParam("img") file: MultipartFile, @CookieValue token: String?): ResponseEntity<String> {
        var verify: Boolean
        if (token != null) {
            if (token.isEmpty() or token.isBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
            else verify = tokens.authenticateToken(token)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)
        return if (Regex("image/.*").containsMatchIn(file.contentType.toString())) {
            var ext = ""
            when (file.contentType.toString()) {
                "image/png" -> ext = ".png"
                "image/jpeg" -> ext = ".jpeg"
                "image/gif" -> ext = ".gif"
            }
            val filename = UUID.randomUUID().toString() + ext
            fileStorage.store(file, "solditem", filename)
            ResponseEntity(filename, HttpStatus.CREATED)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}