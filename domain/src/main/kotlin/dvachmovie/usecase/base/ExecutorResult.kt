package dvachmovie.usecase.base

interface ExecutorResult {
    suspend fun onSuccess(useCaseModel: UseCaseModel)
    suspend fun onFailure(t: Throwable)
}
