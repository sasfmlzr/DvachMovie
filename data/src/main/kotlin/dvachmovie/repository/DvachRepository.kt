package dvachmovie.repository

import dvachmovie.api.DvachMovieApi
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class DvachRepository @Inject constructor(private val dvachApi: DvachMovieApi,
                                          logger: Logger) : BaseRepository(logger) {

    suspend fun getThreads(board: String) = safeApiCall(call = { dvachApi.getCatalogCoroutines(board).await() },
            errorMessage = "GetThreadsFromDvach return error")
}