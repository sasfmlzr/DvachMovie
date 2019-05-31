package dvachmovie.pipe

abstract class PipeAsync<in Input> : Pipe{

    abstract suspend fun execute(input: Input)

}
