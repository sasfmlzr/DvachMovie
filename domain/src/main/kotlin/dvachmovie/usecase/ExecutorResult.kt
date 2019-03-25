package dvachmovie.usecase

interface ExecutorResult {
    fun onSuccess(useCaseModel: UseCaseModel)
    fun onFailure(t: Throwable)
}
