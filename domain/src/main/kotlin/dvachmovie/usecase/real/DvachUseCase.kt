package dvachmovie.usecase.real

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.ForcedStartCallback
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val getThreadUseCase: GetThreadsFromDvachUseCase,
                                       private val getLinkFilesFromThreadsUseCase:
                                       GetLinkFilesFromThreadsUseCase,
                                       private val movieUtils: MovieUtils) : UseCase<DvachUseCase.Params, Unit>(),
        ForcedStartCallback {

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult
    private lateinit var counterWebm: CounterWebm

    private var listThreadSize = 0
    private var count = 0

    private var isForceStart = false

    private val fileItems = mutableListOf<FileItem>()

    override fun forceStart() {
        if (fileItems.isNotEmpty()) {
            isForceStart = true
        } else {
            executorResult.onFailure(RuntimeException("Current request is not containing movies"))
        }
    }

    override suspend fun execute(input: Params) {
        try {
            board = input.board
            executorResult = input.executorResult
            counterWebm = input.counterWebm
            val inputModel = GetThreadsFromDvachUseCase.Params(input.board)
            val useCaseModel = getThreadUseCase.execute(inputModel)
            listThreadSize = useCaseModel.listThreads.size
            counterWebm.updateCountVideos(listThreadSize)



            useCaseModel.listThreads.forEach { num ->
                if (!isForceStart) {
                    fileItems.addAll(executeLinkFilesUseCase(num))
                }
            }

            val webmItems = movieUtils.filterFileItemOnlyAsWebm(fileItems)
            val movies = movieUtils.convertFileItemToMovie(webmItems, board)
            if (movies.isEmpty()) {
                executorResult.onFailure(RuntimeException("This is a private board"))
            } else {
                finally(movies)
            }
        } catch (e: Exception) {
            executorResult.onFailure(e)
        }
    }

    private suspend fun executeLinkFilesUseCase(num: String): List<FileItem> {
        return try {
            val inputModel = GetLinkFilesFromThreadsUseCase.Params(board, num)
            val useCaseLinkFilesModel = getLinkFilesFromThreadsUseCase
                    .execute(inputModel)
            useCaseLinkFilesModel.fileItems
        } catch (e: Exception) {
            count++
            executorResult.onFailure(e)
            emptyList()
        } finally {
            count++
            counterWebm.updateCurrentCountVideos(count)
        }
    }

    private fun finally(listMovies: List<Movie>) {
        executorResult.onSuccess(DvachModel(listMovies))
    }

    data class Params(val board: String,
                      val counterWebm: CounterWebm,
                      val executorResult: ExecutorResult)
}
