package dvachmovie.repository.local

import javax.inject.Singleton

@Singleton
data class MovieTempRepository(val movieLists: MutableList<Movie> = mutableListOf())