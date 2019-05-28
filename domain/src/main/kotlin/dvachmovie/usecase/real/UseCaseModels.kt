package dvachmovie.usecase.real

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.UseCaseModel

data class GetThreadsFromDvachModel(val listThreads: List<String>) : UseCaseModel

data class GetLinkFilesFromThreadsModel(val fileItems: List<FileItem>) : UseCaseModel

data class DvachReportModel(val message: String) : UseCaseModel

data class DvachModel(val movies: List<Movie>) : UseCaseModel

data class ErrorModel(val throwable: Throwable) : UseCaseModel
