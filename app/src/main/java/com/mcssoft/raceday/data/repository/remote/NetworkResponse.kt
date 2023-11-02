package com.mcssoft.raceday.data.repository.remote

import retrofit2.Response

/**
 * Data class to wrap the Retrofit Response.
 * Based on: https://www.youtube.com/watch?v=KWJH7Ns1Bfk
 */
data class NetworkResponse<T>(
    val status: Status,
    val data: Response<T>?,  // an OkHttp.Response<BaseDto> object.
    val ex: Exception?,
    val code: Int,
) {
    companion object {
        fun <T> success(data: Response<T>): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Success,
                data = data,
                ex = null,
                code = data.code()
            )
        }

        fun <T> exception(exception: Exception): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Exception,
                data = null,
                ex = exception,
                code = 0
            )
        }

        fun <T> error(code: Int): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Error,
                data = null,
                ex = null,
                code = code
            )
        }
    }

    sealed class Status {
        data object Success : Status()
        data object Exception : Status()
        data object Error : Status()
    }

    val exception: Boolean
        get() = this.status == Status.Exception

    val error: Boolean
        get() = !exception && this.data?.isSuccessful == false

    val errorCode: Int
        get() = this.code

    val successful: Boolean
        get() = !exception && this.data?.isSuccessful == true

    val body: T
        get() = this.data?.body()!!
}
/*
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}
 */