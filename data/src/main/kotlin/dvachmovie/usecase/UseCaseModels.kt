package dvachmovie.usecase

import dvachmovie.api.FileItem
import dvachmovie.db.data.MovieEntity

data class GetThreadsFromDvachModel(val listThreads: List<String>) : UseCaseModel

data class GetLinkFilesFromThreadsModel(val fileItems: List<FileItem>) : UseCaseModel

data class DvachModel(val movies: List<MovieEntity>) : UseCaseModel

data class DvachReportModel(val message: String) : UseCaseModel
