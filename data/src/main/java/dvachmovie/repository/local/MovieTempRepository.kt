package dvachmovie.repository.local

import javax.inject.Singleton

@Singleton
data class MovieTempRepository(val movieLists: MutableList<Movie> = mutableListOf(),
                               var currentMovie: Movie = Movie()) {

    fun getIndexPosition(): Int {
        var pos = 0
        if (movieLists.contains(currentMovie)) {
            pos = movieLists.indexOf(currentMovie)
        }
        return pos
    }

}