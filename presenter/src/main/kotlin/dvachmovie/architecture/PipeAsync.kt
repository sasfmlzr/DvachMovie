package dvachmovie.architecture

abstract class PipeAsync<in Input> : Pipe {

    abstract suspend fun execute(input: Input)

}
