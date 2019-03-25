package dvachmovie.usecase

import dvachmovie.api.model.thread.FileItem

data class GetThreadsFromDvachModel(val listThreads: List<String>) : UseCaseModel

data class GetLinkFilesFromThreadsModel(val fileItems: List<FileItem>) : UseCaseModel
