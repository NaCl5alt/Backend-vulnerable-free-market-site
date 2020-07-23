package com.example.freemarket

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FreemarketApplication

val logger: Logger = LoggerFactory.getLogger(FreemarketApplication::class.java)
fun main(args: Array<String>) {
    //SpringApplication.run(FreemarketApplication::class.java, *args)
    runApplication<FreemarketApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
    logger.info("Hello Spring boot")
}
