package com.example.freemarket.service

import com.example.freemarket.model.Users
import com.example.freemarket.repo.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserServiceImpl(private val repository: UsersRepository) : UserService {
    /*
    @Autowired
    private lateinit var bpassencode: BCryptPasswordEncoder
    */

    @Transactional(timeout = 10)
    override fun store(user: Users) {
        //user.pass = bpassencode.encode(user.pass)
        repository.save(user)
    }

    override fun findByUserid(userid: String): Users? {
        return repository.findByUserid(userid)
    }


}