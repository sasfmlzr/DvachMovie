package dvachmovie.usecase.real

import dvachmovie.api.FileItem
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.utils.MovieUtils
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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

    private var count = 0

    private val movies = mutableListOf<Movie>()

    private lateinit var networkJob: Job
    private var returnJob: Job? = null

    suspend fun forceStart() {
        returnJob?.cancel()
        networkJob.cancel()

        if (movies.isNotEmpty()) {
            returnMovies()
        } else {
            executorResult.onFailure(RuntimeException("Current request is not containing movies"))
        }
    }

    override suspend fun execute(input: Params) {
        returnJob?.cancel()
        var listThreadSize: Int
        board = input.board
        executorResult = input.executorResult!!
        val inputModel = GetThreadsFromDvachUseCase.Params(input.board)
        networkJob = scopeProvider.ioScope.launch(Job()) {
            try {
                val useCaseModel = getThreadUseCase.execute(inputModel)
                listThreadSize = useCaseModel.listThreads.size

                executorResult.onSuccess(DvachAmountRequestsUseCaseModel(listThreadSize))

                for (num in useCaseModel.listThreads) {
                    try {

                        val webmItems =
                                movieUtils.filterFileItemOnlyAsWebm(executeLinkFilesUseCase(num))
                        movies.addAll(movieUtils.convertFileItemToMovie(webmItems, board))

                    } catch (e: Exception) {
                        if (e is CancellationException) {
                            break

                        } else {
                            executorResult.onFailure(e)
                        }
                    }
                }

                if (networkJob.isActive) {
                    returnMovies()
                }
            } catch (e: Exception) {
                executorResult.onFailure(e)
            }
        }
        networkJob.join()
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
            executorResult.onSuccess(DvachCountRequestUseCaseModel(count))
        }
    }

    private suspend fun returnMovies() {
        returnJob = scopeProvider.ioScope.launch(Job()) {
            try {
                if (movies.isEmpty()) {
                    executorResult.onFailure(RuntimeException("This is a private board or internet problem"))
                } else {
                    executorResult.onSuccess(DvachUseCaseModel(movies))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                executorResult.onFailure(e)
            }
        }
        returnJob!!.join()
    }

    data class Params(val board: String,
                      val executorResult: ExecutorResult? = null) : UseCaseModel
}
