package com.example.study.common.exception

import org.springframework.http.HttpStatus

class CustomException(override val message: String, httpStatus: HttpStatus) : RuntimeException(message) {
    private val httpStatus: HttpStatus
    init {
        this.httpStatus = httpStatus
    }
}
