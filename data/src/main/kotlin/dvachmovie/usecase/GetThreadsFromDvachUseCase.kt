package dvachmovie.usecase

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.model.catalog.DvachCatalogRequest
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.BaseRepository
import dvachmovie.repository.DvachRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

open class GetThreadsFromDvachUseCase @Inject constructor(private val dvachApi: DvachMovieApi,
                                                     private val logger: Logger,
                                                          private val dvachRepository: DvachRepository) {

    companion object {
        private const val TAG = "DvachUseCase"
    }

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult

    fun addParams(board: String,
                  executorResult: ExecutorResult): GetThreadsFromDvachUseCase {
        this.board = board
        this.executorResult = executorResult
        return this
    }

    suspend fun execute() {
        logger.d(TAG, "connecting to 2.hk...")
        return  try {
            val request = dvachRepository.getThreads(board)
            val numThreads = request?.threads?.map { it.num } ?: listOf()
            executorResult.onSuccess(GetThreadsFromDvachModel(numThreads))
            logger.d(TAG, "2.hk connected")
        }catch (e: Exception) {
            executorResult.onFailure(e)
        }

    }
}
