package com.mcssoft.racedaybasic.utility

data class DataResult<T>(
    val status: Status? = null,
    var data: T? = null,
    var response: Int = 0,
    val exception: Exception? = null
) {
    companion object {
        fun <T> loading(): DataResult<T> {
            return DataResult(
                status = Status.Loading,
                data = null,
                exception = null,
                response = 0
            )
        }
        fun <T> error(code: Int): DataResult<T> {
            return DataResult(
                status = Status.Error,
                data = null,
                exception = null,
                response = code
            )
        }
        fun <T> failure(exception: Exception): DataResult<T> {
            return DataResult(
                status = Status.Failure,
                data = null,
                exception = exception,
                response = 0
            )
        }
        fun <T> success(data: T): DataResult<T> {
            return DataResult(
                status = Status.Success,
                data = data,
                exception = null,
                response = 0
            )
        }
    }

    sealed class Status {
        data object Loading : Status()
        data object Error : Status()
        data object Failure : Status()
        data object Success : Status()
    }

    val loading: Boolean
        get() = this.status == Status.Loading

    val failed: Boolean
        get() = exception != null

    val errorCode: Int
        get() = response

    val successful: Boolean
        get() = !failed && this.data != null

}


