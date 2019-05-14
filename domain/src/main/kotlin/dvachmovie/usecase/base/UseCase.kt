package dvachmovie.usecase.base

interface UseCase {

    suspend fun execute(): UseCaseModel
}