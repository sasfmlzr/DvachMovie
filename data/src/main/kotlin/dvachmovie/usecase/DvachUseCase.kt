package dvachmovie.usecase

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCase
import dvachmovie.usecase.real.GetThreadsFromDvachUseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val getThreadUseCase: GetThreadsFromDvachUseCase,
                                       private val getLinkFilesFromThreadsUseCase:
                                       GetLinkFilesFromThreadsUseCase) : UseCase<DvachUseCase.Params, Unit>() {

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult
    private lateinit var counterWebm: CounterWebm

    private var listThreadSize = 0
    private var count = 0
    private var fileItems = mutableListOf<FileItem>()

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
                executeLinkFilesUseCase(num)
            }
        } catch (e: Exception) {
            executorResult.onFailure(e)
        }
    }

    private suspend fun executeLinkFilesUseCase(num: String) {
        try {
            val inputModel = GetLinkFilesFromThreadsUseCase.Params(board, num)
            val useCaseLinkFilesModel = getLinkFilesFromThreadsUseCase
                    .execute(inputModel)
            count++
            counterWebm.updateCurrentCountVideos(count)
            fileItems.addAll(useCaseLinkFilesModel.fileItems)

            if (count == listThreadSize) {
                fileItems = MovieUtils.filterFileItemOnlyAsWebm(fileItems) as MutableList<FileItem>
                if (fileItems.isEmpty()) {
                    executorResult.onFailure(RuntimeException("This is a private board"))
                } else {
                    finally(MovieUtils.convertFileItemToMovieEntity(fileItems, board))
                }
            }
        } catch (e: Exception) {
            count++
            if (count == listThreadSize && fileItems.isNotEmpty()) {
                finally(MovieUtils.convertFileItemToMovieEntity(fileItems, board))
            }
            executorResult.onFailure(e)
        }
    }

    private fun finally(listMovies: List<Movie>) {
        executorResult.onSuccess(DvachModel(listMovies))
    }

    data class Params(val board: String,
                      val counterWebm: CounterWebm,
                      val executorResult: ExecutorResult)
}
