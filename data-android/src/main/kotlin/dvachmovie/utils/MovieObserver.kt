package dvachmovie.utils

interface MovieObserver {
    suspend fun observeDB()
    fun observeDB(onGetMovie: LocalMovieObserver.OnGetMovieListener)
}
