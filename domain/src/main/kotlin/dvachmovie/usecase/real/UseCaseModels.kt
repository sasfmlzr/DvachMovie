package dvachmovie.usecase.real

import dvachmovie.api.FileItem
import dvachmovie.usecase.base.UseCaseModel

data class GetThreadsFromDvachModel(val listThreads: List<String>) : UseCaseModel

data class GetLinkFilesFromThreadsModel(val fileItems: List<FileItem>) : UseCaseModel

data class DvachReportModel(val message: String) : UseCaseModel
