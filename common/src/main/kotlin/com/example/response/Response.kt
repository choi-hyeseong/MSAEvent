package com.example.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class Response<T>(
    val success : Boolean,
    val message : String?,
    val data : T?
) {

    companion object {

        fun<T> of(success: Boolean, message: String?, data : T?) : Response<T> =
            Response(success, message, data)
    }
}