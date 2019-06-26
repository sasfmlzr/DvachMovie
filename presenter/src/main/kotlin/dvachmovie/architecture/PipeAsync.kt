package dvachmovie.architecture

abstract class PipeAsync<in Input, out Output> : Pipe {

    abstract suspend fun execute(input: Input)

}
