package com.erik.common.exception

enum class HTTPErrorResponseStatus {
    NOT_FOUND,
    BAD_REQUEST,
    INTERNAL_SERVER_ERROR,
}

class ErrorResponseWrapper(
    val status: HTTPErrorResponseStatus,
    val description: String
)
