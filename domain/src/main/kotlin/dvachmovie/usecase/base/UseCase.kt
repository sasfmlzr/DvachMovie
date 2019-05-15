package dvachmovie.usecase.base

abstract class UseCase<in Input, Output> {

    abstract suspend fun execute(input: Input): Output

}
