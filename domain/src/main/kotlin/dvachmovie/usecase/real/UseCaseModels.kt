package dvachmovie.usecase.real

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.db.data.Thread
import dvachmovie.usecase.base.UseCaseModel

data class GetThreadsFromDvachUseCaseModel(val listThreads: List<String>) : UseCaseModel

data class GetThreadsFromFourchUseCaseModel(val listThreads: List<Pair<Int, String>>) : UseCaseModel

data class GetLinkFilesFromThreadsUseCaseModel(val fileItems: List<FileItem>) : UseCaseModel

data class DvachReportUseCaseModel(val message: String) : UseCaseModel

data class DvachUseCaseModel(val movies: List<Movie>,
                             val threads: List<Thread>) : UseCaseModel

data class DvachAmountRequestsUseCaseModel(val max: Int) : UseCaseModel

data class DvachCountRequestUseCaseModel(val count: Int) : UseCaseModel
