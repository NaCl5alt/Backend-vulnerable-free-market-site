package com.example.freemarket.filestorage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream

interface FileStorage {
    fun store(file: MultipartFile, path: String, filename: String)
    fun loadFile(filename: String): Resource
    fun deleteFile(filename: String)
    fun deleteAll()
    fun init()
    fun loadFiles(): Stream<Path>
}