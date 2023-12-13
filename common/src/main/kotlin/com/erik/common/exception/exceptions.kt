package com.erik.common.exception

class NotFoundException(msg: String) : RuntimeException(msg) {
    constructor() : this("")
}

class InternalServerException(msg: String) : RuntimeException(msg) {
    constructor() : this("")
}

class AlreadyExistsException(msg: String): RuntimeException(msg) {
    constructor() : this("")
}
