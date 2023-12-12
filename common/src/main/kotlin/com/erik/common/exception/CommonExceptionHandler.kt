package com.erik.common.exception

import org.springframework.core.annotation.Order
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@ControllerAdvice
@Order(1)
class CommonExceptionHandler {

    @ExceptionHandler
    fun handleNotFound(e: NotFoundException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.NOT_FOUND,
                    description = e.message ?: "Resource not found"
                )
            )

    @ExceptionHandler
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message
                )
            )

    @ExceptionHandler
    fun handlePropertyReference(e: PropertyReferenceException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message
                )
            )

    @ExceptionHandler
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message ?: "Request parameters not correct"
                )
            )

    @ExceptionHandler
    fun handleIndexOutOfBounds(e: IndexOutOfBoundsException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message ?: "Request parameters not correct"
                )
            )

    @ExceptionHandler
    fun handleEmptyResultDataAccess(e: EmptyResultDataAccessException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message ?: "Request parameters not correct"
                )
            )

    @ExceptionHandler
    fun handleMethodArgumentTypeMismatch(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message ?: "Request parameters not correct"
                )
            )

    @ExceptionHandler
    fun handleMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponseWrapper> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseWrapper(
                    status = HTTPErrorResponseStatus.BAD_REQUEST,
                    description = e.message ?: "Request parameters not correct"
                )
            )
}
