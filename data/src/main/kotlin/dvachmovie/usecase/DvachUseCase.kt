package dvachmovie.usecase

import dvachmovie.api.model.thread.FileItem
import dvachmovie.data.BuildConfig
import dvachmovie.db.data.MovieEntity
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val dvachUseCase: GetThreadsFromDvachUseCase,
                                       getLinkFilesFromThreadsUseCase:
                                       GetLinkFilesFromThreadsUseCase) : UseCase {

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult
    private lateinit var counterWebm: CounterWebm

    private var listThreadSize = 0
    private var count = 0
    private var fileItems = mutableListOf<FileItem>()

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

    override fun execute() {
        dvachUseCase.addParams(board, counterWebm, dvachUseCaseExecutorResult).execute()
    }

    private val dvachUseCaseExecutorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            useCaseModel as GetThreadsFromDvachModel
            listThreadSize = useCaseModel.listThreads.size
            counterWebm.updateCountVideos(listThreadSize)

            useCaseModel.listThreads.forEach { num ->
                getLinkFilesFromThreadsUseCase
                        .addParams(board, num, getLinkFilesFromThreadsUseCaseResult)
                        .execute()
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
                    convertFileItemToMovieEntity(fileItems)
                }
            }
        }

        override fun onFailure(t: Throwable) {
            count++
            executorResult.onFailure(t)
        }
    }

    private fun convertFileItemToMovieEntity(fileItems: MutableList<FileItem>) {
        val listMovies = mutableListOf<MovieEntity>()
        fileItems.forEach { fileItem ->
            if (fileItem.path.contains(".webm")) {
                val movieEntity = MovieEntity(board = this.board,
                        movieUrl = BuildConfig.DVACH_URL + fileItem.path,
                        previewUrl = BuildConfig.DVACH_URL + fileItem.thumbnail)
                listMovies.add(movieEntity)
            }
        }
        finally(listMovies)
    }

    private fun finally(listMovies: MutableList<MovieEntity>) {
        executorResult.onSuccess(DvachModel(listMovies))
    }
}
