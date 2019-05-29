package dvachmovie.pipe

import dvachmovie.PresenterModel
import dvachmovie.api.Cookie
import dvachmovie.db.data.Movie

data class CookieModel(val cookie: Cookie) : PresenterModel

data class ErrorModel(val throwable: Throwable) : PresenterModel

data class DvachModel(val movies: List<Movie>) : PresenterModel

data class ShuffledMoviesModel(val movies: List<Movie>) : PresenterModel
