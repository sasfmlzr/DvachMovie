package dvachmovie.usecase

interface ExecutorResult {
    fun onSuccess()
    fun onFailure(t: Throwable)
}