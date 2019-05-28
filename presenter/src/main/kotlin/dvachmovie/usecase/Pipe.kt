package dvachmovie.usecase

abstract class Pipe<in Input> {

    abstract suspend fun execute(input: Input)

}
