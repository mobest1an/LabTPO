package com.erik.common.exception

enum class HTTPErrorResponseStatus {
    NOT_FOUND,
    BAD_REQUEST,
    INTERNAL_SERVER_ERROR,
    CONFLICT,
}

class ErrorResponseWrapper(
    val status: HTTPErrorResponseStatus,
    val description: String
)
