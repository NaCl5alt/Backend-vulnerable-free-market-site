package com.example.freemarket.controller

import com.example.freemarket.repo.SearchSoldItemRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.sql.*
import javax.ws.rs.FormParam

@RestController
class SearchController {
    @GetMapping("/search")
    fun searchItem(@FormParam("keyword") keyword: String?): ResponseEntity<String> {
        if (keyword.isNullOrBlank()) return ResponseEntity(HttpStatus.BAD_REQUEST)
        var conn: Connection? = null
        var stmt: Statement? = null
        var res: ResultSet?
        var ans = "["
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://192.168.1.3:5432/freemarket",
                    "postgres", "postgres")
            stmt = conn.createStatement()
            res = stmt.executeQuery("select * from item where name like '%${keyword}%'")
            while (res.next()) {
                ans += "{\n" +
                        "\"id\": \"${res.getString(1)}\",\n" +
                        "\"created_at\": \"${res.getString(2)}\",\n" +
                        "\"explanation\": \"${res.getString(3)}\",\n" +
                        "\"img\": \"${res.getString(4)}\",\n" +
                        "\"name\": \"${res.getString(5)}\",\n" +
                        "\"price\": ${res.getString(6)},\n" +
                        "\"update_at\": \"${res.getString(7)}\"\n" +
                        "},\n"
            }
            ans += "]"
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return ResponseEntity(ex.toString(), HttpStatus.OK)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ResponseEntity(ex.toString(), HttpStatus.OK)
        } finally {
            try {
                if (stmt != null) {
                    conn?.close()
                }
            } catch (ex: SQLException) {
            }
            try {
                conn?.close()
            } catch (ex: SQLException) {
                ex.printStackTrace()
            }
        }

        val headers = HttpHeaders()
        headers.contentType = MediaType("text", "json", Charset.forName("utf-8"))

        return ResponseEntity(ans, headers, HttpStatus.OK)
    }
}