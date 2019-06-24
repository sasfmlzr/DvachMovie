package dvachmovie.storage

import dvachmovie.db.data.Movie
import dvachmovie.db.data.NullMovie
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalMovieStorageTest {

    private lateinit var localMovieStorage: LocalMovieStorage

    private val testMovie = NullMovie()
    private val testListMovies = listOf(NullMovie(), NullMovie("test"))

    @Before
    fun `Set up`() {
        localMovieStorage = LocalMovieStorage()
    }

    @Test
    fun `Setting values in storage was successful`() {
        localMovieStorage.onMovieChangedListener = object : OnMovieChangedListener {
            override fun onMovieChanged(movie: Movie) {
                Assert.assertEquals(testMovie, movie)
            }
        }

        localMovieStorage.setCurrentMovieAndUpdate(testMovie)

        Assert.assertEquals(testMovie, localMovieStorage.currentMovie)

        localMovieStorage.onMovieListChangedListener = object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) {
                Assert.assertEquals(testListMovies, movies)
            }
        }

        localMovieStorage.setMovieListAndUpdate(testListMovies)

        Assert.assertEquals(testListMovies, localMovieStorage.movieList)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `Setting movie in storage was wrong`() {
        localMovieStorage.setCurrentMovieAndUpdate(testMovie)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `Setting list movies in storage was wrong`() {
        localMovieStorage.setMovieListAndUpdate(testListMovies)
    }
}
