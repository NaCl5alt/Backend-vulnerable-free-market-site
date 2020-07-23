package com.example.freemarket.service

import com.example.freemarket.model.Users
import com.example.freemarket.repo.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserServiceImpl(private val repository: UsersRepository) : UserService {
    @Transactional(timeout = 10)
    override fun save(user: Users) {
        repository.save(user)
    }

    override fun findByUserid(userid: String): Users? {
        return repository.findByUserid(userid)
    }

    override fun findAll(): Iterable<Users>? {
        return repository.findAll()
    }

}