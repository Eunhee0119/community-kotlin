package com.example.study

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ControllerHandler {

    private val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validExceptionHandle(ex : MethodArgumentNotValidException): ResponseEntity<ExceptionResponse>? {
        val message = ex.bindingResult.allErrors[0].defaultMessage!!
        log.warn("error " + message + "[NOT_VALID]")
        return ResponseEntity.badRequest().body<ExceptionResponse?>(ExceptionResponse(message))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun badRequestHandler(ex: IllegalArgumentException): ResponseEntity<ExceptionResponse> {
        log.warn("error " + ex.message + "[BAD_REQUEST]")
        return ResponseEntity.badRequest().body<ExceptionResponse?>(ExceptionResponse(ex.message!!))

    }

    @ExceptionHandler(Exception::class)
    fun defaultExceptionHandler(ex: Exception): ResponseEntity<ExceptionResponse> {
        log.warn("error " + ex.message)
        return ResponseEntity.badRequest().body<ExceptionResponse?>(ExceptionResponse(ex.message!!))

    }
}

