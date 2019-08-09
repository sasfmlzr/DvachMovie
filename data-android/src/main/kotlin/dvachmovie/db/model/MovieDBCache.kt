package dvachmovie.db.model

import dvachmovie.db.data.Movie
import dvachmovie.db.data.Thread

object MovieDBCache {
    var movieList: List<Movie> = listOf()
    var threadList: List<Thread> = listOf()
}
