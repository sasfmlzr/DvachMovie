package dvachmovie.pipe

abstract class PipeSync<in Input, out Output> : Pipe{

    abstract fun execute(input: Input): Output

}
