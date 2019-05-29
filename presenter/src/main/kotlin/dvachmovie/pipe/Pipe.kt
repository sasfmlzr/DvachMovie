package dvachmovie.pipe

abstract class Pipe<in Input> {

    abstract fun execute(input: Input)

}
