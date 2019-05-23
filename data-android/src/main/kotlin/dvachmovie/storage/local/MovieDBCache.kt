package dvachmovie.storage.local

import dvachmovie.db.data.Movie

object MovieDBCache {
    var movieList: List<Movie> = listOf()
}
