package dvachmovie.usecase

import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.UseCaseModel

data class DvachModel(val movies: List<Movie>) : UseCaseModel
