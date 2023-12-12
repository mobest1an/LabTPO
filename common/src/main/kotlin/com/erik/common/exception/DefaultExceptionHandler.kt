package com.erik.common.exception

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(1000)
class DefaultExceptionHandler {

    @ExceptionHandler
    fun handleUnknownExceptions(e: Exception): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.INTERNAL_SERVER_ERROR,
                    description = e.message ?: "Something went wrong"
                )
            )
}
