package dvachmovie.pipe

import dvachmovie.PresenterModel
import dvachmovie.api.Cookie
import dvachmovie.db.data.Movie
import dvachmovie.db.data.Thread

data class ErrorModel(val throwable: Throwable) : PresenterModel

data class DvachModel(val movies: List<Movie>,
                      val threads: List<Thread>) : PresenterModel

data class ShuffledMoviesModel(val movies: List<Movie>) : PresenterModel

data class AmountRequestsModel(val max: Int) : PresenterModel

data class CountCompletedRequestsModel(val count: Int) : PresenterModel

data class ReportModel(val message: String) : PresenterModel
