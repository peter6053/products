package com.peter.pezesha.data.network.base

import com.peter.pezesha.utils.exceptions.NetworkIsNotAvailableException
import com.peter.pezesha.utils.platform.NetworkHandler
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response


abstract class BaseRepository(
    private val networkHandler: NetworkHandler,
) {

    protected fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
    ): Result<R> {
        if (!networkHandler.isNetworkAvailable())
            return Result.failure(NetworkIsNotAvailableException())

        return processRequest(call, transform)
    }

    private fun <T, R> processRequest(call: Call<T>, transform: (T) -> R): Result<R> =
        runCatching {
            val response = call.execute()

            // if server error, then try again
            if (response.code().isServerError())
                processResponse(call.clone().execute(), transform)
            else
                processResponse(response, transform)
        }.getOrElse {
            Result.failure(it)
        }

    protected open fun <T, R> processResponse(
        response: Response<T>,
        transform: (T) -> R,
    ): Result<R> {
        if (!response.isSuccessful)
            return Result.failure(HttpException(response))

        if (response.body() == null) {
            return Result.failure(
                NullPointerException("Response body is null")
            )
        }

        return Result.success(transform(response.body()!!))
    }

    private companion object {
        const val SERVER_ERROR_CLASS = 5
        const val TO_RESPONSE_CLASS = 100

        fun Int.isServerError() = this / TO_RESPONSE_CLASS == SERVER_ERROR_CLASS
    }
}
