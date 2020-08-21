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

    companion object {
        private const val VIDEOS_SHOULD_BE_REMOVED_AFTER_COUNT_DAYS = 7
    }

    override suspend fun execute(input: Unit) {
        val movies = getMoviesFromDBUseCase.executeAsync(Unit)

        val notPlayedMovies = movies.filter { movie ->
            !movie.isPlayed && movie.dateAddedToDB < LocalDateTime()
                    .minusDays(VIDEOS_SHOULD_BE_REMOVED_AFTER_COUNT_DAYS)
        }

        useCase.executeAsync(notPlayedMovies)
    }
}
