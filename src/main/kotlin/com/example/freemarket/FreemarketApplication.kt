package com.example.freemarket

import com.example.freemarket.filestorage.FileStorage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FreemarketApplication {
    @Autowired
    lateinit var fileStorage: FileStorage

    @Bean
    fun run() = CommandLineRunner {
        //fileStorage.deleteAll()
        fileStorage.init()
    }
}

val logger: Logger = LoggerFactory.getLogger(FreemarketApplication::class.java)
fun main(args: Array<String>) {
    runApplication<FreemarketApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
    logger.info("Hello Spring boot")
}
