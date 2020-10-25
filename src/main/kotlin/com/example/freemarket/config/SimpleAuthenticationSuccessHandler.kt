package com.example.freemarket.config

import com.example.freemarket.controller.Token
import com.example.freemarket.logger
import com.example.freemarket.model.Account
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SimpleAuthenticationSuccessHandler : AuthenticationSuccessHandler {
    val token = Token()

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         auth: Authentication?) {


        if (response.isCommitted) {
            logger.info("Response has already been committed.")
            return
        }

        val account: Account

        if (auth == null) {
            logger.info("auth null")
            return
        } else {
            account = auth.principal as Account
        }

        response.status = HttpStatus.OK.value()
        response.contentType = "application/json"
        var out = response.writer

        val tokenValue = token.createToken(account.users)

        out.print("{\"token\": \"" + tokenValue + "\"}")

        clearAuthenticationAttributes(request)
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the
     * session during the authentication process.
     */
    private fun clearAuthenticationAttributes(request: HttpServletRequest) {
        val session = request.getSession(false) ?: return
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
    }
}