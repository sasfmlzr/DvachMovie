package dvachmovie.usecase.base

interface ExecutorResult {
    fun onSuccess(useCaseModel: UseCaseModel)
    fun onFailure(t: Throwable)
}
