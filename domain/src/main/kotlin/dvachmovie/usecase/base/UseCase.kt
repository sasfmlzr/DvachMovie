package dvachmovie.usecase.base

abstract class UseCase<in Input, Output> {

    open suspend fun executeAsync(input: Input): Output = throw RuntimeException("Must be implemented")

    open fun execute(input: Input): Output = throw RuntimeException("Must be implemented")
}
