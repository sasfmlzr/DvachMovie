package dvachmovie.usecase

interface InitWebm {
    fun initWebm(listMovies: List<String>)
    fun countVideoUpdates(count: Int)
    fun countVideoCalculatedSumm(summCount: Int)
}