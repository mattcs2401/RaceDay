package com.mcssoft.racedaybasic.utility

data class DataResult<T>(
    val status: Status? = null,
    var data: T? = null,
    val exception: Exception? = null
) {
    companion object {
        fun <T> success(data: T): DataResult<T> {
            return DataResult(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): DataResult<T> {
            return DataResult(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }

        fun <T> loading(): DataResult<T> {
            return DataResult(
                status = Status.Loading,
                data = null,
                exception = null
            )
        }
    }

    sealed class Status {
        object Success : DataResult.Status()
        object Failure : DataResult.Status()
        object Loading : DataResult.Status()
    }

    val loading: Boolean
        get() = this.status == Status.Loading

    val failed: Boolean
        get() = exception != null

    val successful: Boolean
        get() = !failed && this.data != null

}

//fun <T> DataResult<T>.successOr(fallback: T): T {
//    return (this as? DataResult.Status.Success<T>)?.data ?: fallback
//}

