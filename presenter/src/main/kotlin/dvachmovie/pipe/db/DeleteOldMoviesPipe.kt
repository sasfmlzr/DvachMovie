package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.db.DeleteMoviesUseCase
import dvachmovie.usecase.db.GetMoviesFromDBUseCase
import org.joda.time.LocalDateTime
import javax.inject.Inject

class DeleteOldMoviesPipe @Inject constructor(
        private val useCase: DeleteMoviesUseCase,
        private val getMoviesFromDBUseCase: GetMoviesFromDBUseCase
) : PipeAsync<Unit, Unit>() {

    override suspend fun execute(input: Unit) {
        val movies = getMoviesFromDBUseCase.executeAsync(Unit)

        val notPlayedMovies = movies.filter { movie ->
            !movie.isPlayed && movie.dateAddedToDB < LocalDateTime().minusDays(7)
        }

        useCase.executeAsync(notPlayedMovies)
    }
}
