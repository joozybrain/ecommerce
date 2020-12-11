package com.govtech.ecommerce.exception

import org.springframework.http.HttpStatus
import javax.lang.model.type.ErrorType

class ErrorResponse(
    status: HttpStatus,
    val message: String,
    var stackTrace: String? = null)
{
    val code: Int
    lateinit var statusName: String

    init {
        code = status.value()
        statusName = status.name
    }

}