package com.govtech.ecommerce.controller

import com.govtech.ecommerce.exception.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.PrintWriter
import java.io.StringWriter

@ControllerAdvice
class ApiExceptionController : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun internalServerErrorException(ex: Exception)
    : ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
            "Generic Error",
        ex)
    }

    private fun generateErrorResponse(
        status: HttpStatus,
        message: String,
        e: Exception
    ) : ResponseEntity<ErrorResponse> {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        val stackTrace = sw.toString()

        return ResponseEntity(ErrorResponse(status, message, stackTrace), status)
    }
}