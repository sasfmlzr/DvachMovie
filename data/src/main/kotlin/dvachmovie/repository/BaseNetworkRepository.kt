package dvachmovie.repository

import dvachmovie.architecture.logging.Logger
import retrofit2.Response

abstract class BaseNetworkRepository constructor(private val logger: Logger) {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: Result<T> = safeApiResult(call)
        var data: T? = null

        when (result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                logger.d("REPOSITORY", "$errorMessage & Exception - ${result.exception}")
                throw result.exception
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(RuntimeException("Server return ${response.code()} code"))
    }
}
