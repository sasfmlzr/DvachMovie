package dvachmovie.usecase

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.model.catalog.DvachCatalogRequest
import dvachmovie.architecture.logging.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetThreadsFromDvachUseCase @Inject constructor(private val dvachApi: DvachMovieApi,
                                                     private val logger: Logger) : UseCase {

    companion object {
        private const val TAG = "DvachUseCase"
    }

    private lateinit var board: String
    private lateinit var counterWebm: CounterWebm
    private lateinit var executorResult: ExecutorResult

    fun addParams(board: String,
                  counterWebm: CounterWebm,
                  executorResult: ExecutorResult): GetThreadsFromDvachUseCase {
        this.board = board
        this.counterWebm = counterWebm
        this.executorResult = executorResult
        return this
    }

    override fun execute() {
        logger.d(TAG, "connects to 2.hk...")
        dvachApi.getCatalog(board).enqueue(dvachNumCallback(board))
    }

    private fun dvachNumCallback(board: String): Callback<DvachCatalogRequest> {
        return object : Callback<DvachCatalogRequest> {
            override fun onResponse(call: Call<DvachCatalogRequest>,
                                    response: Response<DvachCatalogRequest>) {
                val numThreads = response.body()?.threads?.map { it.num } ?: listOf()
                executorResult.onSuccess(GetThreadsFromDvachModel(numThreads))
                logger.d(TAG, "2.hk connected")
            }

            override fun onFailure(call: Call<DvachCatalogRequest>, t: Throwable) {
                executorResult.onFailure(t)
            }
        }
    }
}
