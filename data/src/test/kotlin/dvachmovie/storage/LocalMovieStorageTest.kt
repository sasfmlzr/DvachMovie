package dvachmovie.storage

import dvachmovie.db.data.Movie
import dvachmovie.db.data.NullMovie
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalMovieStorageTest {

    private lateinit var localMovieStorage: LocalMovieStorage

    @Before
    fun `Set up`() {
        localMovieStorage = LocalMovieStorage()
    }

    @Test
    fun `Setting values in storage was successful`() {
        val testMovie = NullMovie()

        localMovieStorage.onMovieChangedListener = object : OnMovieChangedListener {
            override fun onMovieChanged(movie: Movie) {
                Assert.assertEquals(testMovie, movie)
            }
        }

        localMovieStorage.currentMovie = testMovie

        Assert.assertEquals(testMovie, localMovieStorage.currentMovie)

        val testListMovies = listOf(NullMovie(), NullMovie("test"))
        localMovieStorage.onMovieListChangedListener = object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) {
                Assert.assertEquals(testListMovies, movies)
            }
        }

        localMovieStorage.movieList = testListMovies

        Assert.assertEquals(testListMovies, localMovieStorage.movieList)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `Setting movie in storage was wrong`() {
        val testMovie = NullMovie()

        localMovieStorage.currentMovie = testMovie
    }


    @Test(expected = UninitializedPropertyAccessException::class)
    fun `Setting list movies in storage was wrong`() {
        val testListMovies = listOf(NullMovie(), NullMovie("test"))

        localMovieStorage.movieList = testListMovies
    }
}
