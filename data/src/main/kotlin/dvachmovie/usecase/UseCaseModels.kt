package dvachmovie.usecase

import dvachmovie.db.data.MovieEntity
import dvachmovie.usecase.base.UseCaseModel

data class DvachModel(val movies: List<MovieEntity>) : UseCaseModel
