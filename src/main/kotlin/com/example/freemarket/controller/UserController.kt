package com.example.freemarket.controller

import com.example.freemarket.logger
import com.example.freemarket.model.Users
import com.example.freemarket.repo.UsersRepository
import com.example.freemarket.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.*


@RestController
class UserController(private val service: UserService) {
    @Autowired
    lateinit var repository: UsersRepository

    @PostMapping("/user"/*, produces = [MediaType.TEXT_PLAIN_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE]*/)
    fun regist(@RequestBody user: Users): ResponseEntity<Users> {
        logger.info("regist user")
        val res = service.findByUserid(user.userid)
        if (res != null) return ResponseEntity(HttpStatus.BAD_REQUEST)
        //user.setpass(BCrypt.hashpw(user.getpass(), BCrypt.gensalt()))
        user.pass = BCrypt.hashpw(user.pass, BCrypt.gensalt())
        service.store(user)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/user")
    fun findAll() = repository.findAll()

    @RequestMapping("/user/save")
    fun save(): String {
        //repository.save(Users(UUID.randomUUID(), "asdfghjkl", "test", "test", "test", "test"))
        //repository.save(Users(UUID.randomUUID(), "asdfghjkl", "test2", "test", "test", "test"))
        return "Done"
    }

    @RequestMapping("/user/{userid}")
    fun findByUserid(@PathVariable userid: String) = service.findByUserid(userid)
    /*:ResponseUser{
        val res = service.findByUserid(userid)
        if(res==null) return ResponseUser("","","","",null)
        return ResponseUser(res.userid,res.name,res.profile,res.img,res.id)
    }*/
/*
    data class ResponseUser(
            var userid: String,
            var name: String,
            var profile: String,
            var img: String,
            var id: UUID?
    )*/

}