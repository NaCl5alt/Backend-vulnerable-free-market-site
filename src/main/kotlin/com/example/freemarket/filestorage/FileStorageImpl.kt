package com.example.freemarket.filestorage

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

@Service
class FileStorageImpl : FileStorage {

    val log = LoggerFactory.getLogger(this::class.java)
/*
    val rootLocation = Paths.get("img")
    val userLocation = Paths.get("img/user")
    val itemLocation = Paths.get("img/item")
    val solditemLocation = Paths.get("img/solditem")
*/
        val rootLocation = Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img")
        val userLocation = Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/user")
        val itemLocation = Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/item")
        val solditemLocation = Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/solditem")
    override fun store(file: MultipartFile, path: String, filename: String) {
/*
        when (path) {
            "user" -> if (Files.notExists(Paths.get("img/user/$filename"))) Files.copy(file.inputStream, this.userLocation.resolve(filename))
            "item" -> if (Files.notExists(Paths.get("img/item/$filename"))) Files.copy(file.inputStream, this.itemLocation.resolve(filename))
            else -> if (Files.notExists(Paths.get("img/solditem/$filename"))) Files.copy(file.inputStream, this.solditemLocation.resolve(filename))
        }

 */
        when (path) {
            "user" -> if (Files.notExists(Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/user/$filename"))) Files.copy(file.inputStream, this.userLocation.resolve(filename))
            "item" -> if (Files.notExists(Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/item/$filename"))) Files.copy(file.inputStream, this.itemLocation.resolve(filename))
            else -> if (Files.notExists(Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/solditem/$filename"))) Files.copy(file.inputStream, this.solditemLocation.resolve(filename))
        }
    }

    override fun loadFile(filename: String): Resource {
        val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())

        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("FAIL!")
        }
    }

    override fun deleteFile(filename: String) {
//        if (Files.notExists(Paths.get("img/item/$filename"))) FileSystemUtils.deleteRecursively(Paths.get("img/item/$filename"))
        if (Files.notExists(Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/item/$filename"))) FileSystemUtils.deleteRecursively(Paths.get("/opt/apache-tomcat-9.0.30/webapps/freemarket/img/item/$filename"))
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
        FileSystemUtils.deleteRecursively(userLocation.toFile())
        FileSystemUtils.deleteRecursively(itemLocation.toFile())
        FileSystemUtils.deleteRecursively(solditemLocation.toFile())
    }

    override fun init() {
        if (Files.notExists(rootLocation)) Files.createDirectory(rootLocation)
        if (Files.notExists(userLocation)) Files.createDirectory(userLocation)
        if (Files.notExists(itemLocation)) Files.createDirectory(itemLocation)
        if (Files.notExists(solditemLocation)) Files.createDirectory(solditemLocation)
    }

    override fun loadFiles(): Stream<Path> {
        return Files.walk(this.rootLocation, 1)
                .filter { path -> path != this.rootLocation }
                .map(this.rootLocation::relativize)
    }
}