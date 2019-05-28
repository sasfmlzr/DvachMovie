package dvachmovie.usecase.real

import dvachmovie.api.FileItem
import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.utils.MovieUtils
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val getThreadUseCase: GetThreadsFromDvachUseCase,
                                       private val getLinkFilesFromThreadsUseCase:
                                       GetLinkFilesFromThreadsUseCase,
                                       private val movieUtils: MovieUtils,
                                       private val scopeProvider: ScopeProvider) :
        UseCase<DvachUseCase.Params, Unit>() {

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult
    private lateinit var counterWebm: CounterWebm

    private var listThreadSize = 0
    private var count = 0

    private val fileItems = mutableListOf<FileItem>()

    private lateinit var job: Job

    fun forceStart() {
        if (fileItems.isNotEmpty()) {
            job.cancel()
            //delay(300)
            returnMovies()
        } else {
            executorResult.onFailure(RuntimeException("Current request is not containing movies"))
        }
    }

    override suspend fun execute(input: Params) {

        board = input.board
        executorResult = input.executorResult!!
        counterWebm = input.counterWebm
        val inputModel = GetThreadsFromDvachUseCase.Params(input.board)
        job = scopeProvider.ioScope.launch(Job()) {
            try {
                val useCaseModel = getThreadUseCase.execute(inputModel)
                listThreadSize = useCaseModel.listThreads.size
                counterWebm.updateCountVideos(listThreadSize)

                for (num in useCaseModel.listThreads) {
                    try {
                        fileItems.addAll(executeLinkFilesUseCase(num))
                    } catch (e: Exception) {
                        if (e is CancellationException) {
                            break

                        } else {
                            executorResult.onFailure(e)
                        }
                    }
                }

                if (job.isActive) {
                    returnMovies()
                }
            } catch (e: Exception) {
                executorResult.onFailure(e)
            }
        }
        job.join()
    }

    private suspend fun executeLinkFilesUseCase(num: String): List<FileItem> {
        return try {
            val inputModel = GetLinkFilesFromThreadsUseCase.Params(board, num)
            val useCaseLinkFilesModel = getLinkFilesFromThreadsUseCase
                    .execute(inputModel)
            useCaseLinkFilesModel.fileItems
        } catch (e: Exception) {
            throw e
        } finally {
            count++
            counterWebm.updateCurrentCountVideos(count)
        }
    }

    private fun returnMovies() {
        try {
            val webmItems = movieUtils.filterFileItemOnlyAsWebm(fileItems)
            val movies = movieUtils.convertFileItemToMovie(webmItems, board)
            if (movies.isEmpty()) {
                executorResult.onFailure(RuntimeException("This is a private board or internet problem"))
            } else {
                executorResult.onSuccess(DvachModel(movies))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            executorResult.onFailure(e)
        }
    }

    data class Params(val board: String,
                      val counterWebm: CounterWebm,
                      val executorResult: ExecutorResult? = null) : UseCaseModel
}
