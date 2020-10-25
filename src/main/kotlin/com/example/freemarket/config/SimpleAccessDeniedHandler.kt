package com.example.freemarket.config

import org.springframework.http.HttpStatus
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SimpleAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    fun handle(request: HttpServletRequest?,
               response: HttpServletResponse,
               exception: AccessDeniedException?) {
        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.reasonPhrase)
    }

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: org.springframework.security.access.AccessDeniedException?) {

    }
}