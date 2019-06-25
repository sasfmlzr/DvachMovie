package dvachmovie.utils

interface MovieObserver {
    suspend fun observeDB()
    suspend fun observeDB(onGetMovie: LocalMovieObserver.OnGetMovieListener)
}
