package dvachmovie.usecase

import dvachmovie.api.FileItem
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieUtils
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCase
import dvachmovie.usecase.real.GetThreadsFromDvachModel
import dvachmovie.usecase.real.GetThreadsFromDvachUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val getThreadUseCase: GetThreadsFromDvachUseCase,
                                       getLinkFilesFromThreadsUseCase:
                                       GetLinkFilesFromThreadsUseCase) {

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult
    private lateinit var counterWebm: CounterWebm

    private var listThreadSize = 0
    private var count = 0
    private var fileItems = mutableListOf<FileItem>()

    private val scope = CoroutineScope(Dispatchers.Default)

    fun addParams(board: String,
                  counterWebm: CounterWebm,
                  executorResult: ExecutorResult): DvachUseCase {
        this.board = board
        this.counterWebm = counterWebm
        this.executorResult = executorResult
        count = 0
        fileItems = mutableListOf()
        return this
    }

    suspend fun execute() {
        getThreadUseCase.addParams(board, dvachUseCaseExecutorResult).execute()
    }

    private val dvachUseCaseExecutorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            useCaseModel as GetThreadsFromDvachModel
            listThreadSize = useCaseModel.listThreads.size
            counterWebm.updateCountVideos(listThreadSize)

            scope.launch {
                useCaseModel.listThreads.forEach { num ->
                    getLinkFilesFromThreadsUseCase
                            .addParams(board, num, getLinkFilesFromThreadsUseCaseResult)
                            .execute()
                }
            }
        }

        override fun onFailure(t: Throwable) {
            executorResult.onFailure(t)
        }
    }

    private val getLinkFilesFromThreadsUseCaseResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            useCaseModel as GetLinkFilesFromThreadsModel

            count++
            counterWebm.updateCurrentCountVideos(count)
            fileItems.addAll(useCaseModel.fileItems)

            if (count == listThreadSize) {
                if (fileItems.isEmpty()) {
                    executorResult.onFailure(RuntimeException("This is a private board"))
                } else {
                    finally(MovieUtils.convertFileItemToMovieEntity(fileItems, board))
                }
            }
        }

        override fun onFailure(t: Throwable) {
            count++
            executorResult.onFailure(t)
        }
    }

    private fun finally(listMovies: List<MovieEntity>) {
        executorResult.onSuccess(DvachModel(listMovies))
    }
}
